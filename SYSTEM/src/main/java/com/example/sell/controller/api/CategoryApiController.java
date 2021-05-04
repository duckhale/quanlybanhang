package com.example.sell.controller.api;

import com.example.sell.constanst.RandomData;
import com.example.sell.data.model.Category;
import com.example.sell.data.service.CategoryService;
import com.example.sell.model.resutlData.BaseApiResult;
import com.example.sell.model.resutlData.DataApiResult;
import com.example.sell.model.dto.CategoryDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryApiController {

    private static final Logger logger = LogManager.getLogger(CategoryApiController.class);

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/fake")
    public BaseApiResult fakeCategory() {
        BaseApiResult result = new BaseApiResult();
        List<Category> categories = new ArrayList<>();
        try {
//            Random random = new Random();
            int totalCategories = categoryService.getTotalCategories();
            for (int i = totalCategories + 1; i < totalCategories + 20; i++) {
                Category category = new Category();
//                category.setIdCategory(new RandomData().randomText(6));
                category.setName("Category " + i);
                category.setStatus(true);
                categories.add(category);
            }
            categoryService.addNewListCategories(categories);
            result.setSuccess(true);
            result.setMessage("Fake list category success!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Category>> getListCategories(@RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                                            @RequestParam(value = "pageSize", required = false, defaultValue = "7") int pageSize) {
        return new ResponseEntity<Page<Category>>(categoryService.getPageListCategories(pageNo, pageSize), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public BaseApiResult deleteCategory(@PathVariable int id) {
        BaseApiResult result = new BaseApiResult();
        if (categoryService.deleteCategory(id)) {
            result.setSuccess(true);
            result.setMessage("Delete Success !!");
        } else {
            result.setSuccess(false);
            result.setMessage("Delete false !!");
        }
        return result;
    }

    @PostMapping("/addNew")
    public BaseApiResult addNew(@RequestBody CategoryDTO categoryDTO) {
        BaseApiResult result = new BaseApiResult();
            try {
                Category category = new Category();
                category.setName(categoryDTO.getName());
                category.setStatus(true);
                categoryService.addNewCategory(category);
                result.setSuccess(true);
                result.setMessage("Add new category success: " + category.getIdCategory());
            } catch (Exception e) {
                result.setSuccess(false);
                result.setMessage("Add new category fail!");
                logger.error(e.getMessage());
            }
        return result;
    }

    @PutMapping("/update/{id}")
    public BaseApiResult updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        BaseApiResult result = new BaseApiResult();
        Category categoryEntity = categoryService.findOne(id);
        try {
            categoryEntity.setIdCategory(id);
            categoryEntity.setName(categoryDTO.getName());
            categoryEntity.setStatus(categoryDTO.isStatus());
            categoryService.addNewCategory(categoryEntity);
            result.setMessage("Update category success!");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @GetMapping("/search")
    public BaseApiResult getCategory(@RequestParam(value = "keyword") String keyWord,
                                     @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "7") int pageSize) {
        DataApiResult result = new DataApiResult();

        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        try {
            Page<Category> pageCategory = categoryService.getCategoriesByIdOrName(pageable, keyWord);
            if (pageCategory.isEmpty()) {
                result.setSuccess(false);
                result.setMessage("Not Found");
            } else {
                result.setSuccess(true);
                result.setData(pageCategory);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }
    @GetMapping("/total")
    public DataApiResult getTotalCategory(){
        DataApiResult result = new DataApiResult();
        try {
            result.setSuccess(true);
            result.setData(categoryService.getTotalCategories());
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
