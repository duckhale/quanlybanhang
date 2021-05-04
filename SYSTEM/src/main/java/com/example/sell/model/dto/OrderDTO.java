package com.example.sell.model.dto;

import com.example.sell.data.model.Customer;
import com.example.sell.data.model.Order;
import com.example.sell.data.service.OrderDetailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderDTO {
    private int idCustomer;
    private Customer customerOrder;
    private Date createDate;
    private String status;
    private Double totalMoney;

    OrderDetailService orderDetailService=new OrderDetailService();

    public OrderDTO convertOrder(Order order){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setIdCustomer(order.getIdCustomer());
        orderDTO.setCustomerOrder(order.getCustomerOrder());
        orderDTO.setCreateDate(order.getCreateDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalMoney(order.getTotalMoney());

        return  orderDTO;
    }
}
