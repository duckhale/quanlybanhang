package com.example.sell.data.service;

import com.example.sell.data.model.Order;
import com.example.sell.data.model.OrderDetail;
import com.example.sell.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.logging.LogManager;
//import java.util.logging.Logger;

@Service
public class OrderService {
    // ham logger dung de thong bao loi
    static final Logger loggle = LogManager.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    public  Double getTotalMoney(int idOrder) {
        return orderRepository.totalMoney(idOrder);
    }

    public  Page<Order> getPageListOders(int pageNo, int pageSize) {
        return orderRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    public  Boolean deleteOrder(int id) {
        try {
            orderRepository.deleteById(id);
            return true;

        } catch (Exception e){
            loggle.error(e.getMessage());
            return false;
        }
    }

    public int getTotalOrder() {
        return orderRepository.getTotalOrder();
    }

    @Transactional
    public void addNewListOrder(List<Order> orders) {
        orderRepository.saveAll(orders);
    }

//    public Order findOne(String id) {
//    }

    public Boolean addNewOrder(Order order) {
        try {

            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Page<Order> getOrderByIdOrName(Pageable pageable, String keyWord) {
        return orderRepository.getOrderByIdOrName(pageable, keyWord);
    }

    public List<Order> getAllOrderList() {
        try{
            return  orderRepository.findAll();
        }catch (Exception e){
            return new ArrayList<>();
        }

    }

    public Order findOne(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Page<Order> findAll(Pageable pageable) {
        Page<Order> listPageOrder= orderRepository.findAll(pageable);
        return listPageOrder;
    }

    public Order getOrderById(int id) {
        return  orderRepository.findById(id).orElse(null);
    }

    public Order getOrderByGuid(String idCode) {
        return  orderRepository.getOrderByGuid(idCode);
    }

    public Page<Order> getListOrderByStatus(Pageable pageable, String status) {
        Page<Order> listPageOrder= orderRepository.getListOrderByStatus(pageable,status);
        //return orderRepository.getListOrderByStatus(status);
        return listPageOrder;
    }

    public Page<Order> searchOrderPage(Pageable pageable, String keyWord) {
        return orderRepository.getOrderByIdOrName(pageable,keyWord);
    }


    public void addNewOrderDetail(OrderDetail odrdl) {

    }

    public Order getOne(int idOrder) {
        return orderRepository.findById(idOrder).orElse(null);
    }

    public List<Order> searchOrderById(String keyword) {
        return orderRepository.searchById(keyword);
    }

//    /// phan thong ke
    public Double getAllOrder(int month) {
        return orderRepository.getAllOrder(month);
    }

    public Double getAllMoney(int month) {
        return  orderRepository.getAllMoney(month);
    }

    public Page<Order> getPageOrderByDate(Pageable pageable, int day,int month,int year) {
        Page<Order> pageOrder = orderRepository.getPageOrderByDate(pageable,day,month,year);
        return pageOrder;
    }

    public Page<Order> seachByIdCustomer(Pageable pageable, int idCustomer) {
        Page<Order> pageOrder= orderRepository.getOrderByIdCustomer(pageable,idCustomer);
        return pageOrder;
    }

    public Order getOrderLast() {
        return orderRepository.lastOrder();
    }

    public Page<Order> getPageOrderByYear(Pageable pageable, int year) {
        Page<Order> pageOrder = orderRepository.getPageOrderByYear(pageable,year);
        return pageOrder;
    }

    public Page<Order> getPageOrderByMonth(Pageable pageable, int month, int year) {
        Page<Order> pageOrder = orderRepository.getPageOrderByMonth(pageable,month,year);
        return pageOrder;
    }


//
////    public boolean deleteOrderDetail(String id) {
//    }
}
