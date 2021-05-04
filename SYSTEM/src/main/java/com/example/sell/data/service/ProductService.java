package com.example.sell.data.service;

import com.example.sell.data.model.Product;
import com.example.sell.data.repository.ProductRepository;
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
public class ProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);



    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> get(int idProduct){
        return productRepository.findById(idProduct);
    }

    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public int getTotalProducts(){
        return productRepository.getTotalProducts();
    }

    @Transactional
    public void addNewListProducts(List<Product> products){
        productRepository.saveAll(products);
    }

    public void addNewProduct(Product product){
        try {
            productRepository.save(product);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
    public  Product findOne(int idProduct){
        return productRepository.findById(idProduct).orElse(null);
    }

    public Product getProduct(int idProduct) {
        return productRepository.getProductById(idProduct);
    }

    public  boolean  deleteProduct(int idProduct) {
        try {
            productRepository.deleteById(idProduct);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    public boolean updateProduct(Product product) {
        try {
            productRepository.save(product);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    public Page<Product> getPageListProducts(int pageNo, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    public Page<Product> getProductsByIdOrName(Pageable pageable, String keyword) {
        return productRepository.getProductsByIdOrName(pageable, keyword);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

//    public List<Product> getAllProduct() {
//        return productRepository.findAll();
//    }


}
