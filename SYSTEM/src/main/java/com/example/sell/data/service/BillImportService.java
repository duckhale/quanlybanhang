package com.example.sell.data.service;


import com.example.sell.data.model.BillImport;
import com.example.sell.data.repository.BillImportRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.spi.QueryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BillImportService implements IdentifierGenerator {
    private String prefix = "BILL";

    @Autowired
    private BillImportRepository billImportRepository;


    //Lấy tất cả bill
    public List<BillImport> getAllBillImport(){
        return billImportRepository.findAll();
    }

    //Lấy bill theo id
    public BillImport getBillImportById(int id){
        return billImportRepository.findById(id).orElse(null);
    }

    //Lấy bill theo id supplier
    public List<BillImport> getBillByIdSupplier(int id){
        return billImportRepository.getBillImportByIdSupplier(id);
    }

    //lấy bill vừa thêm mới
    public BillImport getLastBill(){
        return billImportRepository.getLastBill();
    }

    //Lấy bill theo idCode
    public BillImport getBillByIdCode(String idCode){
        return billImportRepository.getBillImportByIdCode(idCode);
    }

    //lấy bill theo month & supplier
    public List<BillImport> getBillBySuppAndMonth(int month,int idSupplier){
        return billImportRepository.getBillBySuppAndMonth(month,idSupplier);
    }

    //Thêm mới bill
    public Boolean addNewBillImport(BillImport billImport) {
        try {
            billImportRepository.save(billImport);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    //Lấy Bill Import theo page
    public Page<BillImport> getAllPage(Pageable pageable) {
        Page<BillImport> listBillImportPage =  billImportRepository.findAll(pageable);
        return listBillImportPage;
    }

    //Xóa Bill
    public Boolean deleteBillImportById(int id) {
        try {
            billImportRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //Tìm kiếm theo tháng
    public List<BillImport> searchByMonth(int month){
        return billImportRepository.searchByMonth(month);
    }

    //Tìm kiếm theo id
    public List<BillImport> searchBillById( String id) {
        return billImportRepository.searchById(id);
    }

    //Tính tổng price trong bill
    public Double getTotalPrice(int idBillImport){
        return billImportRepository.totalPrice(idBillImport);
    }

    //Tính tổng amount trong bill
    public Integer getTotalAmount(int idBillImport){
        return billImportRepository.totalAmount(idBillImport);
    }

    //Total Bill
    public  Double getTotalBill(int month,int year){
        return billImportRepository.totalBillImport(month,year);
    }

    //Total product
    public Double getAllProduct(int month,int year){
        return billImportRepository.totalProduct(month,year);
    }

    //Total product
    public Double getAllMoney(int month,int year){
        return billImportRepository.totalMoney(month,year);
    }


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        QueryImplementor<String> query = session.createQuery("SELECT bill.idCode FROM dbo_bill_import bill", String.class);
        try (Stream<String> stream = query.stream()) {
            Long max = stream.map(t -> t.replace(prefix, "")) // EMP0001 => 0001
                    .mapToLong(Long::parseLong)	// String -> Long
                    .max()						// Tìm số lớn nhất
                    .orElse(0L);				// Nếu không có thì set là 0
            return prefix + String.format("%04d", max + 1); // Tăng lên 1 thành EMP0002
        }
    }
}

