
package com.example.sell.data.service;

import com.example.sell.data.model.OrderDetail;
import com.example.sell.data.model.Product;
import com.example.sell.data.repository.OrderDetailRepository;
import com.example.sell.model.dto.BillImportDetailDTO;
import com.example.sell.model.dto.OrderDetailDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.sell.data.service.OrderService.loggle;

@Service
public class OrderDetailService {
    private static final Logger logger = LogManager.getLogger(OrderDetailService.class);
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetailsByIdOrder(int idOrder) {
        try {
            return orderDetailRepository.getOrderDetailByIdOrder(idOrder);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

//    public void addNewListOrderDetail(List<OrderDetail> orderDetails) {
//        orderDetailRepository.saveAll(orderDetails);
//    }

    public int getTotailOrderDetail() {
        return orderDetailRepository.getTotailOrderDetail();
    }

    public Page<OrderDetail> getPageListOrdersDetail(int pageNo, int pageSize) {
        return orderDetailRepository.findAll(PageRequest.of(pageNo, pageSize));
    }
    // lay tu DTO
    public List<OrderDetailDTO> getAllOrderDetailList() {
        List<OrderDetailDTO> orderDetailDTOS=new ArrayList<OrderDetailDTO>();
        List<OrderDetail> orderDetails=orderDetailRepository.findAll();
        try {
           // return  orderDetailRepository.findAll();
            for (OrderDetail orderDetail: orderDetails){
//                Product product=orderDetail.getProductOrderDetail();


                OrderDetailDTO orderDetailDTO=new OrderDetailDTO().converOrderDetail(orderDetail);
                orderDetailDTOS.add(orderDetailDTO);
            }
            return  orderDetailDTOS;
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<OrderDetail> findAll() {
        List<OrderDetail> listPageOrderDetail= orderDetailRepository.findAll();
        return  listPageOrderDetail;
    }

    public Boolean addNewOrderDetail(OrderDetail orderDetail) {
        try {

            orderDetailRepository.save(orderDetail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public void addNewListOrderDetail(List<OrderDetail> orderDetails) {
        orderDetailRepository.saveAll(orderDetails);
    }

    public OrderDetail findOne(int id) {
        return  orderDetailRepository.findById(id).orElse(null);
    }

    public Boolean deleteOrderDetail(int id) {
        try {
            orderDetailRepository.deleteById(id);
            return true;

        } catch (Exception e){
            loggle.error(e.getMessage());
            return false;
        }
    }
    //Cập nhật số lượng product sau khi Order
    public int updateAmountOrder(int id){

        if (orderDetailRepository.totalAmountOrder(id) != null){
            return orderDetailRepository.totalAmountOrder(id);
        }
        return 0 ;
    }
    public OrderDetail getOrderById(int id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    public OrderDetail getOne(int idOrderDetail) {
        return orderDetailRepository.findById(idOrderDetail).orElse(null);
    }

//    public Page<OrderDetail> searchOrderPage(Pageable pageable, String keyWord) {
//            return  orderDetailRepository.getOrderDetailByIdOrder(pageable,keyWord);
//    }

//    public OrderDetail getOrderById(String id) {
//        return orderDetailRepository.findById(id).orElse(null);
//    }

    //public Object getPageListOdersDetail(int pageNo, int pageSize) {

    //}
//    public int getTotailOrderDetail() {
//    }

}
