package com.midorimart.managementsystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.midorimart.managementsystem.entity.Category;
import com.midorimart.managementsystem.entity.Gallery;
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
import com.midorimart.managementsystem.repository.CountryRepository;
import com.midorimart.managementsystem.repository.GalleryRepository;
import com.midorimart.managementsystem.repository.MerchantRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.ProductUnitRepository;
import com.midorimart.managementsystem.repository.custom.ProductCriteria;
import com.midorimart.managementsystem.service.CommentService;
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
    private final CommentService commentService;
    private final CountryRepository countryRepository;
    private final String FOLDER_PATH = "D:\\FPT_KI_9\\Practice_Coding\\SEP490_G5_Fall2022_Version_1.2\\Midori-mart-project\\public\\images\\product";
    // private final String FOLDER_PATH =
    // "C:\\Users\\AS\\Desktop\\FPT\\FALL_2022\\SEP
    // Project\\midori\\src\\main\\resources\\static\\images";

    @Override
    public String updateDeletedById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDeleted(1);
            product = productRepository.save(product);
            return "Delete successfully";
        }
        return "Delete failed";
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

    // Add new Product
    @Override
    public Map<String, List<String>> addNewProduct(Map<String, ProductDTOCreate> productDTOMap)
            throws CustomNotFoundException, CustomBadRequestException {
        ProductDTOCreate productDTOCreate = productDTOMap.get("product");
        Product product = ProductMapper.toProduct(productDTOCreate);
        Optional<Product> productOptional = productRepository.findByTitle(product.getTitle());
        // Check existed product
        if (productOptional.isEmpty()) {
            Optional<Category> categoryOptional = categoryRepository.findById(productDTOCreate.getCategory());
            Optional<ProductUnit> productUnitOptional = productUnitRepository
                    .findById(productDTOCreate.getProductUnit());
            // Not allow null in category and unit
            if (categoryOptional.isPresent() && productUnitOptional.isPresent()) {
                product.setCategory(categoryOptional.get());
                product.setUnit(productUnitOptional.get());
                product.setAmount(productDTOCreate.getAmount());
                product.setMerchant(merchantRepository.findById(0).get());
            } else {
                throw new CustomNotFoundException(CustomError.builder().code("404")
                        .message("Category, merchant or unit are not existed").build());
            }
            product = productRepository.save(product);
            // Set sku and slug
            if (Integer.valueOf(product.getId()) != null) {
                product.setSku(SkuUtil.getSku(product.getCategory().getId(), product.getId()));
                product.setSlug(SlugUtil.getSlug(product.getTitle(), product.getSku()));
                product = productRepository.save(product);
            }
            Map<String, List<String>> wrapper = new HashMap<>();
            List<String> results = new ArrayList<>();
            results.add(product.getId() + "");
            results.add(product.getSlug());
            wrapper.put("product", results);
            return wrapper;
        }
        throw new CustomBadRequestException(
                CustomError.builder().code("400").message("Already had the same Product name").build());
    }

    // Add new category
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

    // Get Products by filter
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

    // Upload image for products
    @Override
    public Map<String, List<ImageDTOResponse>> uploadImage(MultipartFile[] files, String slug)
            throws IllegalStateException, IOException {
        Map<String, List<ImageDTOResponse>> wrapper = new HashMap<>();
        List<ImageDTOResponse> imageDTOResponses = new ArrayList<>();
        Product product = productRepository.findBySlug(slug).get();
        for (MultipartFile file : files) {
            String url = saveFile(file, product);
            String stringBuilder = url.replace("\\", "/");
            imageDTOResponses.add(ImageDTOResponse.builder().url(stringBuilder).build());
        }
        wrapper.put("images", imageDTOResponses);
        return wrapper;
    }

    // Save image to folder
    private String saveFile(MultipartFile file, Product product) throws IllegalStateException, IOException {
        String filePath = FOLDER_PATH + "\\" + file.getOriginalFilename();
        String fileToSave = "\\images\\product\\" + file.getOriginalFilename();
        Gallery gallery = Gallery.builder().thumbnail(fileToSave).build();
        gallery.setProduct(product);
        gallery = galleryRepository.save(gallery);
        file.transferTo(new File(filePath));
        return gallery.getThumbnail();
    }

    // Search products
    @Override
    public Map<String, List<ProductDTOResponse>> searchProduct(String title, int limit, int offset) {
        String query = "%" + title + "%";
        List<Product> products = productRepository.findByTitleOrSlug(query, limit, offset);
        List<ProductDTOResponse> productDTOResponses = products.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        setAvgStarForProductList(productDTOResponses);
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("products", productDTOResponses);
        return wrapper;
    }

    // Get product detail
    @Override
    public Map<String, ProductDetailDTOResponse> getProductBySlug(String slug) throws CustomNotFoundException {
        Optional<Product> productOptional = productRepository.findBySlug(slug);
        if (productOptional.isPresent()) {
            ProductDetailDTOResponse productDetailDTOResponse = ProductMapper.toProductDetail(productOptional.get());
            setAvgStarForProduct(productDetailDTOResponse);
            Map<String, ProductDetailDTOResponse> wrapper = new HashMap<>();
            wrapper.put("product", productDetailDTOResponse);
            return wrapper;
        }
        throw new CustomNotFoundException(CustomError.builder().code("404").message("Product is not existed").build());
    }

    // Get bestseller in product detail
    @Override
    public Map<String, List<ProductDTOResponse>> getBestSellerInEachCategory(int categoryId) {
        List<Integer> productID = productRepository.findBestSellersInEachCategoryCustom(categoryId);
        List<Product> products = productID.stream().map((id) -> (productRepository.findById(id).get()))
                .collect(Collectors.toList());
        List<ProductDTOResponse> productDTOResponses = products.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        setAvgStarForProductList(productDTOResponses);
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("products", productDTOResponses);
        return wrapper;
    }

    // Set star rate for a product
    private void setAvgStarForProduct(ProductDTOResponse productDTOResponse) {
        Double avgStar = commentService.getAverageStarForEachProduct(productDTOResponse.getId())
                .get("avgStar") == null ? 0
                        : commentService.getAverageStarForEachProduct(productDTOResponse.getId()).get("avgStar");
        productDTOResponse.setStar(avgStar);
    }

    // Set star rate for products list
    private void setAvgStarForProductList(List<ProductDTOResponse> productDTOResponses) {
        for (ProductDTOResponse productDTOResponse : productDTOResponses) {
            setAvgStarForProduct(productDTOResponse);
        }
    }

    // Set star rate for product detail
    private void setAvgStarForProduct(ProductDetailDTOResponse productDTOResponse) {
        Double avgStar = commentService.getAverageStarForEachProduct(productDTOResponse.getId())
                .get("avgStar") == null ? 0
                        : commentService.getAverageStarForEachProduct(productDTOResponse.getId()).get("avgStar");
        productDTOResponse.setStar(avgStar);
    }

    // Get bestseller in Home page
    @Override
    public Map<String, List<ProductDTOResponse>> getBestSellerInHomePage() {
        List<Integer> productID = productRepository.findBestSellersInHomeCustom();
        List<Product> products = productID.stream().map((id) -> (productRepository.findById(id).get()))
                .collect(Collectors.toList());
        List<ProductDTOResponse> productDTOResponses = products.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        setAvgStarForProductList(productDTOResponses);
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("products", productDTOResponses);
        return wrapper;
    }

    // Get top 3 bestseller category in Home Page
    @Override
    public Map<String, List<CategoryDTOResponse>> getTop3BestsellerCategory() {
        List<Integer> categoriesId = productRepository.findBestsellerCategoryCustom();
        List<Category> categories = categoriesId.stream().map((id) -> (categoryRepository.findById(id).get()))
                .collect(Collectors.toList());
        List<CategoryDTOResponse> categoryDTOResponses = categories.stream().map(ProductMapper::toCategoryDTOResponse)
                .collect(Collectors.toList());
        Map<String, List<CategoryDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("categories", categoryDTOResponses);
        return wrapper;
    }

    // Get Top 20 bestsellers in each category
    @Override
    public Map<String, List<ProductDTOResponse>> getTop20BestSellerInEachCategory(int categoryId) {
        List<Integer> productID = productRepository.findTop20BestSellersInCategoryCustom(categoryId);
        List<Product> products = productID.stream().map((id) -> (productRepository.findById(id).get()))
                .collect(Collectors.toList());
        List<ProductDTOResponse> productDTOResponses = products.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        setAvgStarForProductList(productDTOResponses);
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("products", productDTOResponses);
        return wrapper;
    }

    // Edit product
    @Override
    public Map<String, ProductDetailDTOResponse> updateProduct(Map<String, ProductDTOCreate> productDTOMap, String slug)
            throws CustomNotFoundException {
        Product existedProduct = productRepository.findBySlug(slug).get();
        ProductDTOCreate productUpdate = productDTOMap.get("product");
        existedProduct.setAmount(productUpdate.getAmount());
        existedProduct.setCategory(categoryRepository.findById(productUpdate.getCategory()).get());

        // Set title and slug
        if (!checkProductName(productUpdate.getTitle(), existedProduct)) {
            existedProduct.setTitle(productUpdate.getTitle());
            existedProduct.setSlug(SlugUtil.getSlug(productUpdate.getTitle(), existedProduct.getSku()));
        } else
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("Product name already existed").build());
        existedProduct.setDiscount(productUpdate.getDiscount());
        existedProduct.setPrice(productUpdate.getPrice());
        existedProduct.setCountry(countryRepository.findByCode(productUpdate.getOrigin()));
        existedProduct.setDescription(productUpdate.getDescription());
        existedProduct.setUpdated_at(new Date());
        existedProduct.setUnit(ProductUnit.builder().id(productUpdate.getProductUnit()).build());
        existedProduct = productRepository.save(existedProduct);
        return buildDTODetailResponse(existedProduct);
    }

    // Get existed product
    private boolean checkProductName(String title, Product existedProduct) {
        Optional<Product> productOptional = productRepository.findByTitle(title);
        if (productOptional.isPresent()) {
            if (existedProduct.getId() != productOptional.get().getId())
                return true;
        }
        return false;
    }

    // return product to FE
    private Map<String, ProductDetailDTOResponse> buildDTODetailResponse(Product existedProduct) {
        ProductDetailDTOResponse productDetailDTOResponse = ProductMapper.toProductDetail(existedProduct);
        Map<String, ProductDetailDTOResponse> wrapper = new HashMap<>();
        wrapper.put("product", productDetailDTOResponse);
        return wrapper;
    }
}
