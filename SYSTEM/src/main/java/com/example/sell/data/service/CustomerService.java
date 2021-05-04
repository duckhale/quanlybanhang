package com.example.sell.data.service;

import com.example.sell.data.model.Customer;
import com.example.sell.data.model.Product;
import com.example.sell.data.repository.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CustomerService {
    private static final Logger logger = LogManager.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void addNewListCustomer(List<Customer> customers){
        customerRepository.saveAll(customers);
    }


    public List<Customer> getAllListCustomer(){
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public void addNewCustomer(Customer customer){
        try {
            customerRepository.save(customer);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
    public Customer findOne(int id){
        return customerRepository.findById(id).orElse(null);
    }
    public boolean deleteCustomer(int id){
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    public boolean updateCustomer(Customer customer) {
        try {
            customerRepository.save(customer);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }
    public int getTotalCustomers() {
        return customerRepository.getTotalCustomers();
    }
    public Page<Customer> getPageListCustomers(int pageNo, int pageSize) {
        return customerRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    public Page<Customer> getCustomersByIdOrName(Pageable pageable, String keyword) {
        return customerRepository.getCustomersByIdOrName(pageable, keyword);
    }
    public List<Date> getYear(){
        return customerRepository.getYear();
    }
    public Integer getCustomerByYearAndMonth(int year,int month){
        return customerRepository.getCustomerByYearAndMonth(year,month);
    }
    public List<String> getListNameCustomer(){
        return customerRepository.getListNameCustomer();
    }
}
