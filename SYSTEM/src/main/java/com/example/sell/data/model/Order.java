package com.example.sell.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CookieValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "dbo_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order")
    private int idOrder;

    @Column(name = "guid")
    private String guid;

    @Column(name = "id_customer", updatable = false, insertable = false)
    private int idCustomer;

    @ManyToOne( fetch = FetchType.EAGER)
//    @JsonIgnore
    @JoinColumn(name = "id_customer")
    private Customer customerOrder;

//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_money")
    private Double totalMoney;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetails = new ArrayList<>();

//    public void setCustomer(Customer customer) {


//    public void SetIdCustomer(Customer customer) {
//    }
}
