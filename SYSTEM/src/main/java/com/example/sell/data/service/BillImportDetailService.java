package com.example.sell.data.service;


import com.example.sell.data.model.BillImportDetail;
import com.example.sell.data.repository.BillImportDetailRepository;
import com.example.sell.model.dto.BillImportDetailDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillImportDetailService {

    @Autowired
    private BillImportDetailRepository billImportDetailRepository;

    private static final Logger logger= LogManager.getLogger(BillImportDetailService.class);


    //Lấy bill import detail theo id
    public BillImportDetail getBillDetailById(int id){
        return billImportDetailRepository.findById(id).orElse(null);
    }

//    Lấy tất cả bill import detail
//    public List<BillImportDetailDTO> getAllBill(){
//        List<BillImportDetailDTO> listBillDetailDTO = new ArrayList<BillImportDetailDTO>();
//        List<BillImportDetail> listBillDetail = billImportDetailRepository.findAll();
//        try {
//            for(BillImportDetail billImportDetail : listBillDetail){
//                BillImportDetailDTO billImportDetailDTO1 = new BillImportDetailDTO().convertBillDetail(billImportDetail);
//
//                listBillDetailDTO.add(billImportDetailDTO1);
//            }
//            return listBillDetailDTO;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return  new ArrayList<>();
//        }
//    }

    //Lấy bill detail theo id bill
    public List<BillImportDetailDTO> getListByIdBill(int id ){
        List<BillImportDetailDTO> listBillDetailDTO = new ArrayList<>();
        List<BillImportDetail> listBillDetail = billImportDetailRepository.getBillImportDetailByIdBillImport(id);
        try {
            for(BillImportDetail billImportDetail : listBillDetail){
                BillImportDetailDTO billImportDetailDTO = new BillImportDetailDTO().convertBillDetail(billImportDetail);
                listBillDetailDTO.add(billImportDetailDTO);
            }
            return listBillDetailDTO;
        } catch (Exception e) {
            logger.error(e.getMessage());
           return  null;
        }
    }



    //Thêm bill detail
    public  boolean addNewBillDetail(BillImportDetail billImportDetail){
        try {
            billImportDetailRepository.save(billImportDetail);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Xóa bill detail
    public boolean deleteBillDetail(int id){
        try {
            billImportDetailRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //Cập nhật số lượng product sau khi nhập hàng
    public int  updateAmount(int id) {
        if (billImportDetailRepository.totalAmountImport(id) != null) {
            return (billImportDetailRepository.totalAmountImport(id));
        } else {
            return 0;
        }
        // cap nha so luong product khi order


    }
}
