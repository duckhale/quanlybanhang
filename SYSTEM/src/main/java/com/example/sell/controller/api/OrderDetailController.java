package com.example.sell.controller.api;

import com.example.sell.constanst.RandomData;
import com.example.sell.data.model.Category;
import com.example.sell.data.model.Order;
import com.example.sell.data.model.OrderDetail;
import com.example.sell.data.model.Product;
import com.example.sell.data.service.OrderDetailService;
import com.example.sell.data.service.OrderService;
import com.example.sell.data.service.ProductService;
import com.example.sell.model.dto.OrderDetailDTO;
import com.example.sell.model.resutlData.BaseApiResult;
import com.example.sell.model.resutlData.DataApiResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/orderdetail")
@CrossOrigin(origins = "*")
public class OrderDetailController {
    // logger dung de text api
    private static final Logger logger = LogManager.getLogger(OrderDetailController.class);

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
//    @Autowired
//    private int gia

    @GetMapping("/fake")
    public BaseApiResult fakeOrderDetail() {
        BaseApiResult result = new BaseApiResult();

        try {
            List<OrderDetail> orderDetails = new ArrayList<>();
            int totalOrderDetail = orderDetailService.getTotailOrderDetail();
            Random random = new Random();
            RandomData randomData = new RandomData();
            // fake list orderlist
            List<Order> ordersList = orderService.getAllOrderList();
            List<Product> productList = productService.findAll();

            ordersList.stream().forEach(order -> {
//                orderDetail.setIdOrderDetail(random.nextInt(20));
                for (int i = 0; i < 2; i++) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProductOrderDetail(productList.get(random.nextInt(productList.size())));
                    orderDetail.setAmount(100 + i);
                    orderDetail.setTotalPrice(new Random().nextInt(1000));
                    orderDetails.add(orderDetail);
                }
            });
            orderDetailService.addNewListOrderDetail(orderDetails);
            result.setSuccess(true);
            result.setMessage("fake list OrderDetail");

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    // get mapping List lay ra danh sach

    @GetMapping("{id}")
    public DataApiResult getListOrderDetailById(@PathVariable int id) {
        DataApiResult result = new DataApiResult();
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByIdOrder(id);
        try {
            if (!orderDetails.isEmpty()) {
                List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
                for(OrderDetail orderDetail : orderDetails) {
                    orderDetailDTOList.add(new OrderDetailDTO().converOrderDetail(orderDetail));
                }
                result.setSuccess(true);
                result.setData(orderDetailDTOList);
            } else {
                result.setSuccess(false);
                result.setMessage("Not Found");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }

    // lay ds phan theo trang
    @GetMapping("/all")
    public List<OrderDetailDTO> getOrderDetailDTOList() {
        return orderDetailService.getAllOrderDetailList();
    }
    @PostMapping("/addOrderDetail")
    public BaseApiResult addNewOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        BaseApiResult result = new BaseApiResult();
        // OrderDetail orderDetail1=new OrderDetail();

        try {
            Product product=productService.findOne(orderDetailDTO.getIdProduct());
            Order order=orderService.getOrderById(orderDetailDTO.getIdOrder());
            OrderDetail orderDetail1=orderDetailService.getOne(orderDetailDTO.getIdOrderDetail());

            if(orderDetail1==null){
                orderDetail1=new OrderDetail();

                orderDetail1.setIdOrderDetail(orderDetailDTO.getIdOrderDetail());
                orderDetail1.setOrder(order);
                orderDetail1.setIdOrder(orderDetailDTO.getIdOrder());
                orderDetail1.setTotalPrice(orderDetailDTO.getTotalPrice());
                orderDetail1.setIdProduct(orderDetailDTO.getIdProduct());
                orderDetail1.setProductOrderDetail(product);
                orderDetail1.setAmount(orderDetailDTO.getAmount());


                orderDetailService.addNewOrderDetail(orderDetail1);
                result.setSuccess(true);
                result.setMessage("Success add new order detail !");
            } else {
                result.setSuccess(false);
                result.setMessage("Success add fail order !");
            }
            //orderDetailService.addNewOrderDetail(orderDetail);

        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Fail to add new order detail!");
        }
        return result;

    }
    @PutMapping("/update/{id}")
    public BaseApiResult updateOrderDetail(@PathVariable int id, @RequestBody OrderDetail orderDetail) {
        BaseApiResult result = new BaseApiResult();
        OrderDetail odrdl = orderDetailService.findOne(id);

       // odrdl.setIdOrderDetail(orderDetail.getIdOrderDetail());
       // odrdl.setIdOrder(orderDetail.getIdOrder());
//        odrdl.setIdProduct(orderDetail.getIdProduct());
        odrdl.setAmount(orderDetail.getAmount());
        odrdl.setTotalPrice(orderDetail.getTotalPrice());
       // odrdl.setOrder(orderDetail.getOrder());
       // odrdl.setProductOrderDetail(orderDetail.getProductOrderDetail());

        try {
            orderDetailService.addNewOrderDetail(odrdl);
            result.setMessage("Update succes");
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Update fail");
            result.setSuccess(false);
        }
        return result;

    }



@DeleteMapping("/delete")
public BaseApiResult delete(@RequestParam(value = "id", required = true) int id) {
    BaseApiResult result = new BaseApiResult();
    if (orderDetailService.deleteOrderDetail(id)) {
        result.setSuccess(true);
        result.setMessage("Delete success");
    } else {
        result.setSuccess(false);
        result.setMessage("Delete fail");
    }
    return result;
}



}
