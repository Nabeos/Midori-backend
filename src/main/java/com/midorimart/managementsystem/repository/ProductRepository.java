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

        public final String queryForBestSeller = "select top 6 Product_ID from "
                        + "(select *, DENSE_RANK()over(Partition by Category_ID order by total_number desc) as Product_rank from "
                        + "(select distinct c.id as Category_ID, c.name, p.id as Product_ID, sum(quantity) over(partition by product_id) as total_number "
                        + "from Order_Detail od "
                        + "left join Product p on od.product_id = p.id "
                        + "left join Category c on p.category_id = c.id where category_id = :categoryId)AA)BB where Product_rank between 1 and 10 order by Category_ID";

        public final String queryForTop20BestSellerEachCate = "select top 20 Product_ID from "
                        + "(select *, DENSE_RANK()over(Partition by Category_ID order by total_number desc) as Product_rank from "
                        + "(select distinct c.id as Category_ID, c.name, p.id as Product_ID, sum(quantity) over(partition by product_id) as total_number "
                        + "from Order_Detail od "
                        + "left join Product p on od.product_id = p.id "
                        + "left join Category c on p.category_id = c.id where category_id = :categoryId)AA)BB where Product_rank between 1 and 20 order by Category_ID";

        public final String queryForBestSellerInHome = "select Product_ID from "
                        + "(select *, DENSE_RANK()over(Partition by Category_ID order by total_number desc) as Product_rank from "
                        + "(select distinct c.id as Category_ID, c.name, p.id as Product_ID, sum(quantity) over(partition by product_id) as total_number "
                        + "from Order_Detail od "
                        + "left join Product p on od.product_id = p.id "
                        + "left join Category c on p.category_id = c.id where order_id in (select id from [Order] where DATEDIFF(DAY, order_date, GETDATE()) <= 7) and p.deleted = 0)AA)BB order by total_number desc";

        public final String queryForBestCategory = "select category_id from "
                        + "(select *, Row_Number() over(order by total_appear desc) as category_rank from "
                        + "(select distinct category_id, COUNT(product_id) over(partition by category_id) as total_appear from Order_Detail od "
                        + "join Product p on od.product_id = p.id "
                        + "join Category c on p.category_id = c.id)AA)BB where category_rank between 1 and 3";

        Optional<Product> findBySlug(String slug);

        @Query(value = "select * from Product where title Like ?1 or slug LIKE ?1 and deleted = 0 order by created_at desc OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY ", nativeQuery = true)
        List<Product> findByTitleOrSlug(String query, int offset, int limit);

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
