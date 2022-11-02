package com.midorimart.managementsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.product.dto.ProductDTOFilter;
import com.midorimart.managementsystem.repository.CategoryRepository;
import com.midorimart.managementsystem.repository.GalleryRepository;
import com.midorimart.managementsystem.repository.MerchantRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.ProductUnitRepository;
import com.midorimart.managementsystem.repository.custom.ProductCriteria;
import com.midorimart.managementsystem.service.CommentService;

import io.jsonwebtoken.lang.Arrays;

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
        ProductDTOFilter filter = ProductDTOFilter.builder().categoryId(0).priceAsc("asc").priceDesc("desc").limit(0)
                .offset(20).build();
        Map<String, Object> results = new HashMap<>();
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().amount(1).build());
        results.put("listProducts", products);
        results.put("totalProducts", 10);
        Map<String, Object> expected = new HashMap<>();
        expected.put("product", expected);
        expected.put("totalProducts", expected);

        // when
        when(productCriteria.getProductList(filter)).thenReturn(results);
        Map<String, Object> actual = productService.getProductByCategoryId(filter);

        //then
        assertEquals(expected.containsKey("product"), actual.containsKey("product"));
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
