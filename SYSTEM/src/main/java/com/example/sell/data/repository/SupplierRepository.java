package com.example.sell.data.repository;

import com.example.sell.data.model.BillImport;
import com.example.sell.data.model.Supplier;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {


    @Query("select sup from dbo_supplier sup " +
            "where sup.status=:status")
    List<Supplier> getListSupplierByStatus(@Param("status") boolean status) ;

    @Query(value = "SELECT * FROM dbo_supplier WHERE id_supplier=(SELECT MAX(id_supplier) FROM dbo_supplier)", nativeQuery = true)
    Supplier getLastSupplier();

    @Query("select s from dbo_supplier s " +
            "where (upper(s.name) like concat('%',upper(:key),'%') ) "+
            "or s.phoneNumber like concat('%',:key,'%') " +
            "or s.idCode like (:key)")
    List<Supplier> getSuppByName(@Param("key") String key);

    Supplier getSupplierByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
