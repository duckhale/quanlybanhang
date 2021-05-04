package com.example.sell.controller.api;


import com.example.sell.data.model.BillImport;

import com.example.sell.data.service.BillImportService;
import com.example.sell.model.dto.BillImportDTO;
import com.example.sell.model.dto.BillImportDetailDTO;
import com.example.sell.model.resutlData.BaseApiResult;
import com.example.sell.model.resutlData.DataApiResult;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;


import java.util.*;

@RestController
@RequestMapping("api/billImport")
@CrossOrigin(origins = "*")
public class BillImportApiController {

    private static final Logger logger = LogManager.getLogger(CommentApiController.class);

    @Autowired
    private BillImportService billImportService;

    //Lấy tất cả bill
    @GetMapping("/all")
    public List<BillImport> getAllBillImport(){
        return billImportService.getAllBillImport();
    }


    //Lấy bill theo page
    @GetMapping("/billPage")
    public BaseApiResult getListBill(@RequestParam(value = "page") int page){
        DataApiResult result= new DataApiResult();
        List<BillImport> listBill =billImportService.getAllBillImport();
        try {
            Pageable pageable = PageRequest.of(page,5);
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize())>listBill.size() ? listBill.size() : (start + pageable.getPageSize());
            Page<BillImport> listBillPage1=new PageImpl<>(listBill.subList(start,end),pageable,listBill.size());
            result.setData(listBillPage1);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            logger.error(e.getMessage());
        }
        return result;
    }

    //Lấy bill theo id
    @GetMapping("/{id}")
    public BillImport getBillById(@PathVariable int id){
        BillImport billImport = new BillImport();
        billImport = billImportService.getBillImportById(id);
        return  billImport;
    }

    //Lấy bill theo idSupplier

    @GetMapping("/searchByIdSupp")
    public BaseApiResult getListBillByIdSupplier(@RequestParam(value = "page") int page,
                                                 @RequestParam(value = "idSupplier") int idSupplier){
        DataApiResult result= new DataApiResult();
        List<BillImport> listBill =billImportService.getBillByIdSupplier(idSupplier);
        try {

            Pageable pageable = PageRequest.of(page,5);
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize())>listBill.size() ? listBill.size() : (start + pageable.getPageSize());
            Page<BillImport> listBillPage1=new PageImpl<>(listBill.subList(start,end),pageable,listBill.size());
            result.setData(listBillPage1);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            logger.error(e.getMessage());
        }
        return result;
    }

    //Lấy bill theo month & supplier
    @GetMapping("/getByMonthAndSupp")
    public List<BillImport> getBillByMonthAndSupp(@RequestParam(value = "month",defaultValue = "0",required = false) int month ,
                                                  @RequestParam(value = "idSupplier",defaultValue = "0",required = false) int idSupplier){
        if ((month!=0)&&(idSupplier==0)){
            return billImportService.searchByMonth(month);
        } if((month==0)&&(idSupplier!=0)){
                return billImportService.getBillByIdSupplier(idSupplier);
        } if ((month!=0)&&(idSupplier!=0)){
            return billImportService.getBillBySuppAndMonth(month, idSupplier);
        } else {
            return null;
        }

    }


    //Lấy last bill
    @GetMapping("/getLastBill")
    public BillImport getLastBill(){
        return  billImportService.getLastBill();
    }

    //Lấy bill theo idCode
    @GetMapping("/getByIdCode/{idCode}")
    public BillImport getByIdCode(@PathVariable String idCode){
        return billImportService.getBillByIdCode(idCode);
    }

    //Thêm mới bill
    @PostMapping("/addBillImport")
    public BaseApiResult addNewBill(@RequestBody BillImportDTO billImportDTO){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImport billImport = billImportService.getBillImportById(billImportDTO.getIdBillImport());
        if(billImport==null) {
            try {
                billImport = new BillImport();
                if(billImportService.getLastBill()!=null){
                String idCode =  billImportService.getLastBill().getIdCode();



                String stringNumber = idCode.substring(4,9);
                int idCodeNumber = Integer.parseInt(stringNumber);
                idCodeNumber++;
                String numberCode =  String.format("%05d%n",idCodeNumber);
                String fix = "BILL";
                String newIdCode = fix.concat(numberCode).replace("\r\n","");

                billImport.setIdCode(newIdCode);
                } else {
                    billImport.setIdCode("BILL00001");
                }
                billImport.setCreateDate(billImportDTO.getCreateDate());
                billImport.setSupplierImport(billImportDTO.getSupplier());
                billImport.setTotalMoney(billImportDTO.getTotalMoney());
                billImport.setTotalProduct(billImportDTO.getTotalProduct());
                billImportService.addNewBillImport(billImport);
                baseApiResult.setSuccess(true);
                baseApiResult.setMessage("Success add new Bill Import !");
            } catch (Exception e) {
                e.printStackTrace();
                baseApiResult.setSuccess(false);
                baseApiResult.setMessage("Fail to add new Bill Import!");
            }
        } else{
            baseApiResult.setSuccess(false);
            baseApiResult.setMessage("Bill Import đã tồn tại");
        }
        return baseApiResult;



    }

    //Sửa bill
    @PutMapping("/update/{id}")
    public BaseApiResult editBill(@RequestBody BillImportDTO billImportDTO,
                                  @PathVariable int id){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImport billImport = billImportService.getBillImportById(id);

        billImport.setCreateDate(billImportDTO.getCreateDate());

        try{
            billImportService.addNewBillImport(billImport);
            baseApiResult.setMessage("Update success");
            baseApiResult.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            baseApiResult.setMessage("Update fail");
            baseApiResult.setSuccess(false);

        }
        return  baseApiResult;

    }


    //Xóa bill
    @DeleteMapping("delete/{id}")
    public BaseApiResult deleteBill(@PathVariable int id){
        BaseApiResult baseApiResult = new BaseApiResult();
        if(billImportService.deleteBillImportById(id)){
            baseApiResult.setMessage("Delete success");
            baseApiResult.setSuccess(true);
        } else {
            baseApiResult.setMessage("Delete fail");
            baseApiResult.setSuccess(false);
        }
        return  baseApiResult;
    }

    //Search bill
    @GetMapping("/search")
    public List<BillImport> searchBillImport(
                                         @RequestParam(value = "keyWord") String keyWord){

        List<BillImport> listBill = billImportService.searchBillById(keyWord);
        return listBill  ;
    }

    //Search theo tháng
    @GetMapping("/searchMonth")
    public List<BillImport> searchByMonth(@RequestParam(value = "month") int month){
        List<BillImport> listBill = billImportService.searchByMonth(month);
        return listBill;
    }



    @PutMapping("/updateMoney/{id}")
    public  BaseApiResult updateBillImport(@RequestBody BillImportDetailDTO billImportDetailDTO,
                                            @PathVariable int id){
        BaseApiResult baseApiResult = new BaseApiResult();
        BillImport billImport = billImportService.getBillImportById(id);
        billImport.setTotalProduct(billImportService.getTotalAmount(id));
        billImport.setTotalMoney(billImportService.getTotalPrice(id));
        try{
            billImportService.addNewBillImport(billImport);
            baseApiResult.setMessage("Update success");
            baseApiResult.setSuccess(true);
        } catch (Exception e){
            e.printStackTrace();
            baseApiResult.setMessage("Update fail");
            baseApiResult.setSuccess(false);
        }
        return baseApiResult;
    }

    //Thống kê
    @GetMapping("/thongKe")
    public List<Map> thongKe(@RequestParam(value = "year",defaultValue = "2020") int year
                            ){
        List list = new ArrayList();
        for (int i=1;i<=12;i++) {
            Map<String,Double> map = new HashMap<String,Double>();
            map.put("totalBill", billImportService.getTotalBill(i,year));
            map.put("totalProduct", billImportService.getAllProduct(i,year));
            map.put("totalMoney", billImportService.getAllMoney(i,year));
            list.add(map);
        }
         return list;
    }

}
