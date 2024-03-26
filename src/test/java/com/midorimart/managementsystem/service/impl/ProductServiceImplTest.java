package com.midorimart.managementsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.midorimart.managementsystem.entity.Category;
import com.midorimart.managementsystem.entity.Country;
import com.midorimart.managementsystem.entity.Gallery;
import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.ProductQuantity;
import com.midorimart.managementsystem.entity.ProductUnit;
import com.midorimart.managementsystem.model.mapper.ProductMapper;
import com.midorimart.managementsystem.model.product.dto.ProductDTOFilter;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.repository.CategoryRepository;
import com.midorimart.managementsystem.repository.GalleryRepository;
import com.midorimart.managementsystem.repository.MerchantRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.ProductUnitRepository;
import com.midorimart.managementsystem.repository.custom.ProductCriteria;
import com.midorimart.managementsystem.service.CommentService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCriteria productCriteria;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private GalleryRepository galleryRepository;
    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private ProductUnitRepository productUnitRepository;
    @Mock
    private CommentService commentService;

    @Test
    void testAddNewCategory() {
        // given: giả lập tham số đầu vào

        // when

        // then

    }

    @Test
    void testAddNewProduct() {

    }

    @Test
    void testGetAllCategories() {

    }

    @Test
    void testGetBestSellerInEachCategory() {

    }

    @Test
    void testGetProductByCategoryId() {
        // get
        ProductDTOFilter filter = ProductDTOFilter.builder().categoryId(1).priceAsc("asc").priceDesc("desc").limit(20)
                .offset(0).build();
        Map<String, Object> results = new HashMap<>();
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().id(1)
                .slug("slug")
                .sku("sku")
                .title("title")
                .galleries(java.util.Arrays.asList(Gallery.builder().thumbnail("gallery").build()))
                .status(0)
                .deleted(0)
                .price(3.99)
                .discount(0)
                .category(Category.builder().name("Category").build())
                .created_at(new Date())
                .updated_at(new Date())
                .merchant(Merchant.builder()
                        .user(com.midorimart.managementsystem.entity.User.builder().fullname("name").email("email")
                                .build())
                        .id(1).country(Country.builder().name("Country").build()).merchantName("name").build())
                .amount(1)
                .unit(ProductUnit.builder().id(1).name("name").build())
                .productQuantities(java.util.Arrays.asList(ProductQuantity.builder().id(1).build())).build();
        products.add(product);
        Long quantity = 10L;
        results.put("listProducts", products);
        results.put("totalProducts", quantity);
        Map<String, Object> expected = new HashMap<>();
        List<ProductDTOResponse> list = products.stream().map(ProductMapper::toProductDTOResponse)
                .collect(Collectors.toList());
        for (ProductDTOResponse productDTOResponse : list) {
            productDTOResponse.setStar(5);
        }
        expected.put("product", list);
        expected.put("totalProducts", expected);
        // when
        when(productCriteria.getProductList(filter)).thenReturn(results);

        Map<String, Object> actual = productService.getProductByCategoryId(filter);

        // then
        assertEquals(true, actual.containsKey("product"));
    }

    @Test
    void testGetProductBySlug() {

    }

    @Test
    void testSearchProduct() {

    }

    @Test
    void testUpdateDeletedById() {

    }

    @Test
    void testUploadImage() {

    }
}
