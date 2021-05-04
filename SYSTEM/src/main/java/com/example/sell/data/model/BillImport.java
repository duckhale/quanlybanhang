package com.example.sell.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "dbo_bill_import")
public class BillImport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_bill_import")
    private int idBillImport;


    @GeneratedValue(generator = "id_code")
    @GenericGenerator(name = "id_code", strategy = "core.generator.BillImportService")
    private String idCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+7")
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "total_product")
    private Integer totalProduct;

    @Column(name = "total_money")
    private Double totalMoney;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "billImport")
    private List<BillImportDetail> billImportDetails = new ArrayList<>();

    @Column(name = "id_supplier", updatable = false,insertable = false)
    private int idSupplier;

    @ManyToOne(fetch=FetchType.EAGER)
//    @JsonIgnore
    @JoinColumn(name = "id_supplier")
    private Supplier supplierImport;



}
