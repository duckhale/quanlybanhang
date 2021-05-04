package com.example.sell.service;

import com.example.sell.data.model.Admin;
import com.example.sell.data.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("userDetailsService")
public class MySQLDetailService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminService.findAdminByUsername(s);
        if (admin!=null){
            User userDetail = new org.springframework.security.core.userdetails.User(admin.getUsername(),
                    admin.getPassword(),
                    new ArrayList<>());
            return userDetail;
        }
        throw new UsernameNotFoundException(s +"not found");
    }
}
