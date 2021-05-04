package com.example.sell.data.repository;

import com.example.sell.data.model.BillImportDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillImportDetailRepository extends JpaRepository<BillImportDetail,Integer> {


        List<BillImportDetail> getBillImportDetailByIdBillImport(@Param("idBillImport") int idBillImport);

        @Query("select sum(detail.amount) from dbo_bill_import_detail detail " +
                "where detail.idProduct=:idProduct")
        Integer totalAmountImport(@Param("idProduct") int idProduct);


//        @Query("select sum(detail.amount) from dbo_bill_import_detail detail " +
//                "where detail.idProduct=:idProduct")
//        Integer totalAmountImport(@Param("idProduct") String idProduct);

}
