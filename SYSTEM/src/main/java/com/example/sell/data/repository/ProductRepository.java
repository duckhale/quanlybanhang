package com.example.sell.data.repository;

import com.example.sell.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select count(p.idProduct) from dbo_product p")
    int getTotalProducts();

    @Query("select p from dbo_product p where p.idProduct=:id")
    Product getProductById(@Param("id") int id);

    @Query("select p from dbo_product p " +
            "where (upper(p.idProduct) like concat('%',upper(:keyword),'%') ) " +
            "or (upper(p.name) like concat('%',upper(:keyword),'%') )")
    Page<Product> getProductsByIdOrName(Pageable pageable, @Param("keyword") String keyword);






}



