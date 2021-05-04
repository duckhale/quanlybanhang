package com.example.sell.model.dto;

import com.example.sell.data.model.BillImport;
import com.example.sell.data.model.Supplier;
import com.example.sell.data.service.BillImportDetailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillImportDTO {


     BillImportDetailService billImportDetailService = new BillImportDetailService();

    private int idBillImport;
    private Date createDate;
    private int totalProduct;
    private Double totalMoney;
    private Supplier supplier;
    private int idSupplier;



    public BillImportDTO convertBill(BillImport billImport) {
        BillImportDTO billImportDTO = new BillImportDTO();
        billImportDTO.setIdBillImport(billImport.getIdBillImport());
        billImportDTO.setCreateDate(billImport.getCreateDate());
//        billImportDTO.setIdSupplier(billImport.getIdSupplier());
        billImportDTO.setTotalMoney(billImport.getTotalMoney());
        billImportDTO.setTotalProduct(billImport.getTotalProduct());
        billImportDTO.setSupplier(billImport.getSupplierImport());

        return billImportDTO;
    }
}
