package com.example.sell.data.repository;

import com.example.sell.data.model.Comment;
import com.example.sell.data.model.Customer;
import com.example.sell.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.Expression;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {


    @Query("select count(c.idComment) from dbo_comment c")
    int getTotalComment();

    @Query("select cmt from dbo_comment cmt " +
            "where (upper(cmt.product.name) like concat('%',upper(:keyword),'%') )" +
            "or (upper(cmt.customer.name) like concat('%',upper(:keyword),'%' ) )")
    Iterable<Comment> getListCommentByKeyword(@Param("keyword") String keyword);

    @Query("select cmt from dbo_comment cmt " +
            "where cmt.product.name=:nameProduct and cmt.customer.name=:nameCustomer")
    Iterable<Comment> getListCommentByNameProductAndCustomer(@Param("nameProduct") String nameProduct,
                                                             @Param("nameCustomer") String nameCustomer);

    @Query("select cmt from dbo_comment cmt " +
            "where cmt.product.name=:nameProduct")
    Iterable<Comment> getListCommentByNameProduct(@Param("nameProduct") String nameProduct);

    @Query("select cmt from dbo_comment cmt " +
            "where cmt.customer.name=:nameCustomer")
    Iterable<Comment> getListCommentByNameCustomer(@Param("nameCustomer") String nameCustomer);

    @Transactional(readOnly = true)
    @Query("select distinct c.createDate from dbo_comment c ")
    List<Date> getYear();

    @Query(value = "select count(c.idComment) from dbo_comment c " +
            "where c.createDate=:createDate ", nativeQuery = true)
//    List<Comment>
    Integer countCommentByDate(@Param("createDate") Date createDate);

    //    @Procedure(procedureName = "get_comment_by_year_month(:year,:month)")
    @Query(value = "select count(id_comment) from dbo_comment " +
            "where year(create_date)=:year and month(create_date)=:month ", nativeQuery = true)
    Integer getCommentByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
