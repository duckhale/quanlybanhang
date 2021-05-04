package com.example.sell.controller.api;

import com.example.sell.data.model.BillImportDetail;
import com.example.sell.data.service.BillImportDetailService;
import com.example.sell.model.resutlData.BaseApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sell.model.dto.BillImportDetailDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/billDetail")
public class BillImportDetailApiController {

    @Autowired
    private BillImportDetailService billImportDetailService;

    //Lấy bill detail theo idBillimport
    @GetMapping("")
    public List<BillImportDetailDTO> getListBillByIdBillImport(@RequestParam(value = "idBillImport",required = true)int id){
        return billImportDetailService.getListByIdBill(id);
    }

//    @GetMapping("/all")
//    public List<BillImportDetailDTO> getListBillDetail(){
//        return billImportDetailService.getAllBill();
//    }

    //Thêm mới billdetail
    @PostMapping("/addNewBillDetail")
    public BaseApiResult addNewBillDetail(@RequestBody BillImportDetailDTO billImportDetailDTO){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImportDetail billImportDetail = new BillImportDetail();

        billImportDetail.setPrice(billImportDetailDTO.getPrice());
        billImportDetail.setAmount(billImportDetailDTO.getAmount());
        billImportDetail.setProductImport(billImportDetailDTO.getProduct());
        billImportDetail.setBillImport(billImportDetailDTO.getBillImport());
        billImportDetail.setTotalPrice(billImportDetailDTO.getTotalPrice());

        if(billImportDetailService.addNewBillDetail(billImportDetail)){
            baseApiResult.setSuccess(true);
            baseApiResult.setMessage("Success to add new Bill Import !");
        }else {
            baseApiResult.setSuccess(false);
            baseApiResult.setMessage("Fail to add new Bill Import !");
        }
        return baseApiResult;

    }

    //Cập nhật
    @PutMapping("update/{id}")
    public BaseApiResult editBillDetail(@RequestBody BillImportDetailDTO billImportDetailDTO,
                                        @PathVariable int id){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImportDetail billImportDetail = billImportDetailService.getBillDetailById(id);
        billImportDetail.setPrice(billImportDetailDTO.getPrice());
        billImportDetail.setAmount(billImportDetailDTO.getAmount());
        billImportDetail.setProductImport(billImportDetailDTO.getProduct());
        billImportDetail.setTotalPrice(billImportDetailDTO.getTotalPrice());

        if(billImportDetailService.addNewBillDetail(billImportDetail)){
            baseApiResult.setSuccess(true);
            baseApiResult.setMessage("Success to update Bill Import !");
        }else {
            baseApiResult.setSuccess(false);
            baseApiResult.setMessage("Fail to update Bill Import !");
        }
        return baseApiResult;
    }

    //Xóa
    @DeleteMapping("delete/{id}")
    public BaseApiResult deleteBillDetail(@PathVariable int id){
        BaseApiResult baseApiResult = new BaseApiResult();
        if(billImportDetailService.deleteBillDetail(id)){
            baseApiResult.setSuccess(true);
            baseApiResult.setMessage("Success to delete Bill Import !");
        }else {
            baseApiResult.setSuccess(false);
            baseApiResult.setMessage("Fail to delete Bill Import !");
        }
        return baseApiResult;
    }


}
