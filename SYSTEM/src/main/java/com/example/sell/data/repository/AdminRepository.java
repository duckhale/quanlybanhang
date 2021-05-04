package com.example.sell.data.repository;

import com.example.sell.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Query("select a from dbo_admin a " +
            "where a.username=:username")
    Iterable<Admin> findAdminByName(@Param("username") String username);

//    Admin findAdminByUsername(String u)
}
