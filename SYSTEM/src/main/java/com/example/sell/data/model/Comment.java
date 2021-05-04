package com.example.sell.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "dbo_comment")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_comment")
    private int idComment;

    @Column(name = "id_customer", updatable = false, insertable = false)
    private int idCustomer;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Column(name = "id_Product", insertable = false, updatable = false)
    private int idProduct;

    @ManyToOne(optional = true)
    @JsonIgnore
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

}
