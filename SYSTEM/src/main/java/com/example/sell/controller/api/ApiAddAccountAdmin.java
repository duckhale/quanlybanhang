package com.example.sell.controller.api;

import com.example.sell.data.model.Admin;
import com.example.sell.data.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiAddAccountAdmin {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create-admin")
    public String addAdmin(@Validated @ModelAttribute("admin")Admin admin){
        adminService.addNewAdmin(admin);
        return "success!!";
    }
}
