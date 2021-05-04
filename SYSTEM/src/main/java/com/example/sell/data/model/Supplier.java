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
@Entity(name = "dbo_supplier")

public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_supplier")
    private int idSupplier;

    @Column(name = "id_code")
    private String idCode;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "logo")
    private String logo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    private List<Product> productList =  new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierImport", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BillImport> billImportList = new ArrayList<>();

}
