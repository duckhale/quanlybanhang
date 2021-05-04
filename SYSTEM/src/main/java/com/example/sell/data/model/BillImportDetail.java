package com.example.sell.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "dbo_bill_import_detail")
public class BillImportDetail {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id_bill_import_detail")
    private int id;

    @Column(name = "id_bill_import", insertable = false, updatable = false)
    private int idBillImport;

    @ManyToOne(optional = true)
    @JsonIgnore
    @JoinColumn(name = "id_bill_import")
    private BillImport billImport;

    @Column(name = "id_product", insertable = false, updatable = false)
    private int idProduct;

    @ManyToOne(optional = true)
    @JsonIgnore
    @JoinColumn(name = "id_product")
    private Product productImport;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_price")
    private Double totalPrice;

}
