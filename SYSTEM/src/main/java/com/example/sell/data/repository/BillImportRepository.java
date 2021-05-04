package com.example.sell.data.repository;

import com.example.sell.data.model.BillImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillImportRepository extends JpaRepository<BillImport,Integer> {
    @Query("select bill from dbo_bill_import bill " +
            "where bill.idBillImport=:keyWord ")
    List<BillImport> searchById( @Param("keyWord") String keyWord);

    @Query("select sum(detail.totalPrice) from dbo_bill_import_detail detail " +
            "where detail.idBillImport=:idBillImport")
    Double totalPrice(@Param("idBillImport") int idBillImport);

    @Query("select sum(detail.amount) from dbo_bill_import_detail detail " +
            "where detail.idBillImport=:idBillImport")
    Integer totalAmount(@Param("idBillImport") int idBillImport);

    @Query(value = "SELECT * FROM dbo_bill_import WHERE id_bill_import=(SELECT MAX(id_bill_import) FROM dbo_bill_import)", nativeQuery = true)
    BillImport getLastBill();

    List<BillImport> getBillImportByIdSupplier(@Param("idSupplier") int idSupplier);

    BillImport getBillImportByIdCode(@Param("idCode") String idCode);

    @Query("select bill from dbo_bill_import bill" +
            " where month(bill.createDate) =:month")
    List<BillImport> searchByMonth(@Param("month") int month);
//
    @Query("select bill from dbo_bill_import bill" + " where month(bill.createDate) =:month" + " and bill.idSupplier=:idSupplier")
    List<BillImport> getBillBySuppAndMonth(@Param("month") int month,@Param("idSupplier") int idSupplier);
//
    @Query("SELECT count(bill.idBillImport) FROM dbo_bill_import bill where month(bill.createDate) =:month and year(bill.createDate)=:year")
    Double totalBillImport(@Param("month") int month,@Param("year") int year);

    @Query("SELECT sum(bill.totalProduct) FROM dbo_bill_import bill where month(bill.createDate) =:month and year(bill.createDate)=:year")
    Double totalProduct(@Param("month") int month,@Param("year") int year);

    @Query("SELECT sum(bill.totalMoney) FROM dbo_bill_import bill where month(bill.createDate) =:month and year(bill.createDate)=:year")
    Double totalMoney(@Param("month") int month,@Param("year") int year);




}
