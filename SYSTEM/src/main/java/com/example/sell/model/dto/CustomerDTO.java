package com.example.sell.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private int id;
    private String name;
    private String passwordHash;
    private String phoneNumber;
    private String address;
    private String email;
    private Boolean status;
    private int amountBoom;
    private Date createDate;
}
