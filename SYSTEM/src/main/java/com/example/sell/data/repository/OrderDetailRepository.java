package com.example.sell.data.repository;

import com.example.sell.data.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {

    @Query("select count(o.idOrderDetail) from dbo_order_detail o")
    int getTotailOrderDetail();

    @Transactional(readOnly = true)
    @Query("select o from dbo_order_detail o " +
            "where o.idOrder=:idOrder")
    List<OrderDetail> getOrderDetailByIdOrder(@Param("idOrder") int idOrder);


    @Query("select sum(detail.amount) from dbo_order_detail detail " +
            "where detail.idProduct=:idProduct")
    Integer totalAmountOrder(@Param("idProduct") int idProduct);

//    @Query("select  orderdetail from dbo_order_detail orderdetail" +
//            "where (upper(orderdetail.idOrderDetail) like concat('%',upper(:keyword),'%') ) ")
//    Page<OrderDetail> getOrderDetailByIdOrder(Pageable pageable, String keyWord);

    //  Optional<Object> findById(String id);

    // Optional<Object> findById(String id);
}
