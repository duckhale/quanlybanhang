package com.example.sell.controller.api;

import com.example.sell.constanst.GetListYear;
import com.example.sell.constanst.RandomData;
import com.example.sell.data.model.Comment;
import com.example.sell.data.model.Customer;
import com.example.sell.data.model.Product;
import com.example.sell.data.service.CommentService;
import com.example.sell.data.service.CustomerService;
import com.example.sell.data.service.ProductService;
import com.example.sell.model.dto.CommentDTO;
import com.example.sell.model.resutlData.BaseApiResult;
import com.example.sell.model.resutlData.DataApiResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class CommentApiController {
    private static final Logger logger = LogManager.getLogger(CommentApiController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @GetMapping("/fake")
    public BaseApiResult fakeComment() {
        BaseApiResult result = new BaseApiResult();

        try {
            List<Comment> commentList = new ArrayList<>();
            int totalComment = commentService.getTotalComment();
            Random random = new Random();
            RandomData randomData = new RandomData();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2019);
            calendar.set(Calendar.MONTH, 6);
            calendar.set(Calendar.DAY_OF_MONTH, 12);
            List<Customer> customerList = customerService.getAllListCustomer();
            List<Product> productList = productService.findAll();
            for (int i = totalComment + 1; i < totalComment + 5; i++) {
                Comment comment = new Comment();
//                comment.setIdComment(i);
                comment.setCustomer(customerList.get(random.nextInt(customerList.size())));
                comment.setProduct(productList.get(random.nextInt(productList.size())));
                comment.setContent(randomData.randomText(100));
                comment.setImage("https://xixon-knight.000webhostapp.com/product-gym/comment.jpg");
                comment.setCreateDate(calendar.getTime());
                commentList.add(comment);
            }
            commentService.addNewListComment(commentList);
            result.setSuccess(true);
            result.setMessage("Fake list content success!");

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @GetMapping("/getList")
    public BaseApiResult getListComment(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "7") int pageSize,
                                        @RequestParam(name = "nameProduct", required = false, defaultValue = "") String nameProduct,
                                        @RequestParam(name = "nameCustomer", required = false, defaultValue = "") String nameCustomer) {
        DataApiResult result = new DataApiResult();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        try {
            List<Comment> comments;
            if (nameCustomer.isEmpty() && !nameProduct.isEmpty()) {
                comments = (List<Comment>) commentService.getListCommentByNameProduct(nameProduct);
            } else if (!nameCustomer.isEmpty() && nameProduct.isEmpty()) {
                comments = (List<Comment>) commentService.getListCommentByNameCustomer(nameCustomer);
            } else if (!nameCustomer.isEmpty() && !nameProduct.isEmpty()) {
                comments = (List<Comment>) commentService.getListCommentByNameProductAndCustomer(nameProduct, nameCustomer);
            } else {
                comments = commentService.getListComment();
            }
            comments.stream().forEach(comment -> {
                CommentDTO commentDTO = new CommentDTO().convertComment(comment);
                commentDTOS.add(commentDTO);
            });
            Sort sort = Sort.by("id");
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize()) > commentDTOS.size() ? commentDTOS.size() : (start + pageable.getPageSize());
            Page<CommentDTO> page = new PageImpl<>(commentDTOS.subList(start, end), pageable, commentDTOS.size());
            result.setData(page);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            logger.error(e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public BaseApiResult delete(@PathVariable int id) {
        BaseApiResult result = new BaseApiResult();
        if (commentService.deleteCommentById(id)) {
            result.setSuccess(true);
            result.setMessage("Delete success");
        } else {
            result.setSuccess(false);
            result.setMessage("Delete fail");
        }
        return result;
    }


    @GetMapping("/search")
    public BaseApiResult getCommentByKeyword(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                             @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "7") int pageSize) {
        DataApiResult result = new DataApiResult();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        try {
            List<Comment> comments = (List<Comment>) commentService.getListCommentByKeyword(keyword);
            if (!comments.isEmpty()) {
                comments.stream().forEach(comment -> {
                    CommentDTO commentDTO = new CommentDTO().convertComment(comment);
                    commentDTOS.add(commentDTO);
                });
                int start = (int) pageable.getOffset();
                int end = (start + pageable.getPageSize()) > commentDTOS.size() ? commentDTOS.size() : (start + pageable.getPageSize());
                Page<CommentDTO> page = new PageImpl<CommentDTO>(commentDTOS.subList(start, end), pageable, commentDTOS.size());
                result.setData(page);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("Not Found");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @GetMapping("/statistical")
    public DataApiResult StatisticalComment(@RequestParam(name = "year", required = false, defaultValue = "0") int year) {
        DataApiResult result = new DataApiResult();
        Map<String, Object> data = new HashMap<>();
//        Map<String, Integer> commentByMonth = new LinkedHashMap<>();
        List<Integer> years = new ArrayList<>();
        List<Integer> listCountComment = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();
        int yearNow = localDate.getYear();
        int monthNow = localDate.getMonthValue();
        years.addAll(new GetListYear().getYears(commentService.getYear()));
        List<String> months = new ArrayList<>();
        Arrays.asList(new DateFormatSymbols().getShortMonths()).stream().filter(s -> s != "")
                .forEach(s -> {
                    months.add(s);
                });

        if (year == 0) {
            AtomicInteger i = new AtomicInteger(1);
            months.stream()
                    .filter(s -> s != "")
                    .forEach(s -> {
//                        commentByMonth.put(s, commentService.getCommentByYearAndMonth(yearNow, i.get()));
                        listCountComment.add(commentService.getCommentByYearAndMonth(yearNow, i.get()));
                        i.getAndIncrement();
                    });
        } else {
            AtomicInteger i = new AtomicInteger(1);
            months.stream()
                    .filter(s -> s != "")
                    .forEach(s -> {
                        listCountComment.add(commentService.getCommentByYearAndMonth(year, i.get()));
                        i.getAndIncrement();
                    });
        }
        data.put("months", months);
        data.put("years", years);
        data.put("monthNow", monthNow);
        data.put("data", listCountComment);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }
}
