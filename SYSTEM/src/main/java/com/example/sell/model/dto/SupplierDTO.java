package com.example.sell.model.dto;

import com.example.sell.data.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private int idSupplier;
    private String name;
    private String address;
    private Boolean status;
    private String logo;
    private List<Product> productList =  new ArrayList<>();

}
