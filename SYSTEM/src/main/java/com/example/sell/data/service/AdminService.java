package com.example.sell.data.service;

import com.example.sell.data.model.Admin;
import com.example.sell.data.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Admin> findOne(int id){
        return adminRepository.findById(id);
    }

    public Admin findAdminByUsername(String username){
       return StreamSupport
               .stream(adminRepository.findAdminByName(username).spliterator(),false)
               .findFirst().orElse(null);
    }
    public void addNewAdmin(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }
}
