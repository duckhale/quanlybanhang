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
@Entity(name = "dbo_product")
public class Product {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id_product")
    private int idProduct;

    @Column(name = "id_category", insertable = false, updatable = false)
    private int idCategory;

    @ManyToOne(optional = true)
    @JsonIgnore
    @JoinColumn(name = "id_category")
    private Category category;

    @Column(name = "id_supplier", insertable = false, updatable = false)
    private int idSupplier;

    @ManyToOne(optional = true)
    @JsonIgnore
    @JoinColumn(name = "id_supplier")
    private Supplier supplier;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;

    @Column(name = "content")
    private String content;

    @Column(name = "favorite")
    private int favorite;

    @Column(name = "amount")
    private int amount;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Comment> comments = new ArrayList<>();

    //@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productOrderDetail")
    private List<OrderDetail> orderDetails = new ArrayList<>();


    @OneToMany(mappedBy = "productImport", cascade = CascadeType.ALL)
    private List<BillImportDetail> importDetailList = new ArrayList<>();

}
