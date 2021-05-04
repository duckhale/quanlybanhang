package com.example.sell.data.service;

import com.example.sell.data.model.Category;
import com.example.sell.data.repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;
    // get tat ca
    public List<Category> getAllListCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    // add new
    @Transactional
    public void addNewListCategories(List<Category> categories) {
        categoryRepository.saveAll(categories);
    }

    public Category findOne(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void addNewCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    //
    public boolean updateCategory(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    // xoa
    public boolean deleteCategory(int id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    // page
    public int getTotalCategories() {
        return categoryRepository.getTotalCategories();
    }

    @Transactional
    public Page<Category> getPageListCategories(int pageNo, int pageSize) {
        return categoryRepository.findAll(PageRequest.of(pageNo, pageSize));
    }
    // page
    public Page<Category> getCategoriesByIdOrName(Pageable pageable, String keyword) {
        return categoryRepository.getCategoriesByIdOrName(pageable, keyword);
    }
}
