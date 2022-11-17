package com.midorimart.managementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

        public final String queryForBestSeller = "select Product_ID from "
                        + "(select *, DENSE_RANK()over(Partition by Category_ID order by total_number desc) as Product_rank from "
                        + "(select distinct c.id as Category_ID, c.name, p.id as Product_ID, sum(quantity) over(partition by product_id) as total_number "
                        + "from Order_Detail od "
                        + "left join Product p on od.product_id = p.id "
                        + "left join Category c on p.category_id = c.id where category_id = :categoryId)AA)BB where Product_rank between 1 and 5 order by Category_ID";

        public final String queryForTop20BestSellerEachCate = "select top 20 Product_ID from "
                        + "(select *, DENSE_RANK()over(Partition by Category_ID order by total_number desc) as Product_rank from "
                        + "(select distinct c.id as Category_ID, c.name, p.id as Product_ID, sum(quantity) over(partition by product_id) as total_number "
                        + "from Order_Detail od "
                        + "left join Product p on od.product_id = p.id "
                        + "left join Category c on p.category_id = c.id where category_id = :categoryId)AA)BB where Product_rank between 1 and 20 order by Category_ID";

        public final String queryForBestSellerInHome = "select top 20 Product_ID from "
                        + "(select *, DENSE_RANK()over(Partition by Category_ID order by total_number desc) as Product_rank from "
                        + "(select distinct c.id as Category_ID, c.name, p.id as Product_ID, sum(quantity) over(partition by product_id) as total_number "
                        + "from Order_Detail od "
                        + "left join Product p on od.product_id = p.id "
                        + "left join Category c on p.category_id = c.id)AA)BB order by total_number desc";

        public final String queryForBestCategory = "select distinct top 3 Category_ID from "
                        + "(select *, DENSE_RANK()over(Partition by Category_ID order by total_number desc) as Product_rank from "
                        + "(select distinct c.id as Category_ID, p.id as Product_ID, sum(quantity) over(partition by product_id) as total_number "
                        + "from Order_Detail od "
                        + "left join Product p on od.product_id = p.id "
                        + "left join Category c on p.category_id = c.id)AA)BB where Product_rank between 1 and 5";

        Optional<Product> findBySlug(String slug);

        @Query(value = "select * from Product where title Like ?1 or slug LIKE ?1", nativeQuery = true)
        List<Product> findByTitleOrSlug(String query);

        Optional<Product> findByTitle(String title);

        @Query(value = queryForBestSeller, nativeQuery = true)
        List<Integer> findBestSellersInEachCategoryCustom(@Param("categoryId") int category_id);

        @Query(value = queryForTop20BestSellerEachCate, nativeQuery = true)
        List<Integer> findTop20BestSellersInCategoryCustom(@Param("categoryId") int category_id);

        @Query(value = queryForBestSellerInHome, nativeQuery = true)
        List<Integer> findBestSellersInHomeCustom();

        @Query(value = queryForBestCategory, nativeQuery = true)
        List<Integer> findBestsellerCategoryCustom();

}
