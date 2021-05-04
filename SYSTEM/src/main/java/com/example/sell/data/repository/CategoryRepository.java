package com.example.sell.data.repository;

import com.example.sell.data.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("select count (c.idCategory) from dbo_category c")
    int getTotalCategories();

    @Query("select c from dbo_category c " +
            "where (upper(c.idCategory) like concat('%',upper(:keyword),'%') ) " +
            "or (upper(c.name) like concat('%',upper(:keyword),'%') )")
    Page<Category> getCategoriesByIdOrName(Pageable pageable, @Param("keyword") String keyWord);
}
