package com.example.sell.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.omg.IOP.Codec;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "dbo_admin")
public class Admin {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_admin")
    private int idAdmin;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
