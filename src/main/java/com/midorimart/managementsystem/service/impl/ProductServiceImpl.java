package com.midorimart.managementsystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.midorimart.managementsystem.entity.Category;
import com.midorimart.managementsystem.entity.Gallery;
import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.ProductUnit;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.exception.custom.CustomNotFoundException;
import com.midorimart.managementsystem.model.CustomError;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOCreate;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.mapper.ProductMapper;
import com.midorimart.managementsystem.model.product.dto.ImageDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOFilter;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDetailDTOResponse;
import com.midorimart.managementsystem.repository.CategoryRepository;
import com.midorimart.managementsystem.repository.GalleryRepository;
import com.midorimart.managementsystem.repository.MerchantRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.ProductUnitRepository;
import com.midorimart.managementsystem.repository.custom.ProductCriteria;
import com.midorimart.managementsystem.service.ProductService;
import com.midorimart.managementsystem.utils.SkuUtil;
import com.midorimart.managementsystem.utils.SlugUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCriteria productCriteria;
    private final CategoryRepository categoryRepository;
    private final GalleryRepository galleryRepository;
    private final MerchantRepository merchantRepository;
    private final ProductUnitRepository productUnitRepository;
    private final String FOLDER_PATH = "C:\\Users\\AS\\Desktop\\FPT\\FALL_2022\\SEP Project\\midori\\src\\main\\resources\\static\\images";

    @Override
    public void updateDeletedById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDeleted(1);
            product = productRepository.save(product);
        }
    }

    @Override
    public Map<String, List<ProductDTOResponse>> findAllProduct() {
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        List<Product> productList = productRepository.findAll();
        List<ProductDTOResponse> productDTOResponses = new ArrayList<>();
        ProductDTOResponse productDTOResponse;
        for (Product product : productList) {
            productDTOResponse = ProductMapper.toProductDTOResponse(product);
            productDTOResponses.add(productDTOResponse);
        }
        wrapper.put("product", productDTOResponses);
        return wrapper;
    }

    @Override
    public Map<String, String> addNewProduct(Map<String, ProductDTOCreate> productDTOMap)
            throws CustomNotFoundException, CustomBadRequestException {
        ProductDTOCreate productDTOCreate = productDTOMap.get("product");
        Product product = ProductMapper.toProduct(productDTOCreate);
        Optional<Product> productOptional = productRepository.findByTitle(product.getTitle());
        if (productOptional.isEmpty()) {
            Optional<Category> categoryOptional = categoryRepository.findById(productDTOCreate.getCategory());
            Optional<ProductUnit> productUnitOptional = productUnitRepository
                    .findById(productDTOCreate.getProductUnit());
            Optional<Merchant> merchantOptional = merchantRepository.findById(productDTOCreate.getMerchantId());
            if (categoryOptional.isPresent() && productUnitOptional.isPresent() && merchantOptional.isPresent()) {
                product.setCategory(categoryOptional.get());
                product.setMerchant(merchantOptional.get());
                product.setUnit(productUnitOptional.get());
                product.setAmount(productDTOCreate.getAmount());
            } else {
                throw new CustomNotFoundException(CustomError.builder().code("404")
                        .message("Category, merchant or unit are not existed").build());
            }
            product = productRepository.save(product);
            if (Integer.valueOf(product.getId()) != null) {
                product.setSku(SkuUtil.getSku(product.getCategory().getId(), product.getId()));
                product.setSlug(SlugUtil.getSlug(product.getTitle(), product.getSku()));
                product = productRepository.save(product);
            }
            Map<String, String> wrapper = new HashMap<>();
            wrapper.put("product", "ok");
            return wrapper;
        }
        throw new CustomBadRequestException(
                CustomError.builder().code("400").message("Already had the same Product name").build());
    }

    @Override
    public Map<String, CategoryDTOResponse> addNewCategory(Map<String, CategoryDTOCreate> categoryMap)
            throws CustomBadRequestException {
        CategoryDTOCreate categoryDTOCreate = categoryMap.get("category");
        Category category = ProductMapper.toCategory(categoryDTOCreate);
        Optional<Category> categoryOptional = categoryRepository.findByName(category.getName());
        if (categoryOptional.isEmpty()) {
            category = categoryRepository.save(category);

            CategoryDTOResponse categoryDTOResponse = ProductMapper.toCategoryDTOResponse(category);
            Map<String, CategoryDTOResponse> wrapper = new HashMap<>();
            wrapper.put("category", categoryDTOResponse);
            return wrapper;
        }
        throw new CustomBadRequestException(
                CustomError.builder().code("400").message("Already had this category").build());
    }

    @Override
    public Map<String, Object> getProductByCategoryId(ProductDTOFilter filter) {
        Map<String, Object> results = productCriteria.getProductList(filter);
        List<Product> productList = (List<Product>) results.get("listProducts");
        Long totalProducts = (Long) results.get("totalProducts");

        // convert to ProductDTOResponse
        List<ProductDTOResponse> listProductDTOResponses = productList.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("product", listProductDTOResponses);
        wrapper.put("totalProducts", totalProducts);
        return wrapper;
    }

    @Override
    public Map<String, List<CategoryDTOResponse>> getAllCategories() {
        List<Category> listCategories = categoryRepository.findAll();
        List<CategoryDTOResponse> listCategoryDTOResponses = new ArrayList<>();
        for (Category category : listCategories) {
            listCategoryDTOResponses.add(ProductMapper.toCategoryDTOResponse(category));
        }
        Map<String, List<CategoryDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("categories", listCategoryDTOResponses);
        return wrapper;
    }

    @Override
    public Map<String, ImageDTOResponse> uploadImage(MultipartFile[] files) throws IllegalStateException, IOException {
        for (MultipartFile file : files) {
            saveFile(file);
        }
        return null;
    }

    private String saveFile(MultipartFile file) throws IllegalStateException, IOException {
        String filePath = FOLDER_PATH + "\\" + file.getOriginalFilename();
        Gallery gallery = galleryRepository.save(Gallery.builder().thumbnail(filePath).build());
        file.transferTo(new File(filePath));
        return gallery.getThumbnail();
    }

    @Override
    public Map<String, List<ProductDTOResponse>> searchProduct(String title) {
        String query = "%" + title + "%";
        List<Product> products = productRepository.findByTitleOrSlug(query);
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        List<ProductDTOResponse> productDTOResponses = products.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        wrapper.put("products", productDTOResponses);
        return wrapper;
    }

    @Override
    public Map<String, ProductDetailDTOResponse> getProductBySlug(String slug) throws CustomNotFoundException {
        Optional<Product> productOptional = productRepository.findBySlug(slug);
        if (productOptional.isPresent()) {
            ProductDetailDTOResponse productDetailDTOResponse = ProductMapper.toProductDetail(productOptional.get());
            Map<String, ProductDetailDTOResponse> wrapper = new HashMap<>();
            wrapper.put("product", productDetailDTOResponse);
            return wrapper;
        }
        throw new CustomNotFoundException(CustomError.builder().code("404").message("Product is not existed").build());
    }

    @Override
    public Map<String, List<ProductDTOResponse>> getBestSellerInEachCategory(int categoryId) {
        List<Integer> productID = productRepository.findBestSellersInEachCategoryCustom(categoryId);
        List<Product> products = productID.stream().map((id)->(productRepository.findById(id).get())).collect(Collectors.toList());
        List<ProductDTOResponse> productDTOResponses = products.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("products", productDTOResponses);
        return wrapper;
    }

}
