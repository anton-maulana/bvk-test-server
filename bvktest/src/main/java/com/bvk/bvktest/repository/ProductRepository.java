package com.bvk.bvktest.repository;

import com.bvk.bvktest.model.Product;
import com.bvk.bvktest.model.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products p " +
            "WHERE  IFNULL(p.name, '') LIKE %:keyword% " +
            "ORDER BY p.name",
            countQuery = "SELECT COUNT(p.id) FROM products p " +
                    "WHERE  IFNULL(p.name, '') LIKE %:keyword% " , nativeQuery = true)
    Page<List<Product>> findProducts(
        Pageable pageable,
        @Param("keyword")
        String keyword
    );
}
