package com.example.sell.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "dbo_order_detail")
public class OrderDetail {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id_order_detail")
    private int idOrderDetail;

    @Column(name = "id_order",updatable = false,insertable = false)
    private int idOrder;

    @ManyToOne(optional = true,fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "id_order")
    private Order order;

    @Column(name = "id_product",insertable = false,updatable = false)
    private int idProduct;

    @ManyToOne (optional = true,fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "id_product")
    private Product productOrderDetail;

    @Column(name = "amount")
    private int amount;

    @Column(name ="total_price")
    private double totalPrice;


}
