package com.example.sell.data.repository;

import com.example.sell.data.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("select count (ct.id) from dbo_customer ct")
    int getTotalCustomers();

    @Query("select c.name from dbo_customer c")
    List<String> getListNameCustomer();

    @Query("select ct from dbo_customer ct " +
            "where (upper(ct.name) like concat('%',upper(:keyword),'%') )" +
            "or (upper(ct.phoneNumber) like concat('%',upper(:keyword),'%' ) )" +
            "or (upper(ct.email) like  concat('%',upper(:keyword),'%' ) )")
    Page<Customer> getCustomersByIdOrName(Pageable pageable, @Param("keyword") String keyWord);

    @Transactional(readOnly = true)
    @Query("select distinct c.createDate from dbo_customer c ")
    List<Date> getYear();

    @Query(value = "select count(id) from  dbo_customer " +
            "where year(create_date)=:year and month(create_date)=:month ", nativeQuery = true)
    Integer getCustomerByYearAndMonth(@Param("year") int year, @Param("month") int month);
}

