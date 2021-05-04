package com.example.sell.data.service;

import com.example.sell.data.model.Comment;
import com.example.sell.data.repository.CommentRepository;
import com.example.sell.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private static final Logger logger = LogManager.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void addNewListComment(List<Comment> commentList) {
        commentRepository.saveAll(commentList);
    }

    public Comment findComment(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    public int getTotalComment() {
        return commentRepository.getTotalComment();
    }

    public List<Comment> getListComment() {
        try {
            return commentRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean deleteCommentById(int id) {
        try {
            commentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    public Iterable<Comment> getListCommentByKeyword(String keyword) {
        return commentRepository.getListCommentByKeyword(keyword);
    }

    public Iterable<Comment> getListCommentByNameProductAndCustomer(String nameProduct,String nameCustomer) {
        try {
            return commentRepository.getListCommentByNameProductAndCustomer(nameProduct,nameCustomer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Iterable<Comment> getListCommentByNameProduct(String nameProduct) {
        try {
            return commentRepository.getListCommentByNameProduct(nameProduct);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Iterable<Comment> getListCommentByNameCustomer(String nameCustomer) {
        try {
            return commentRepository.getListCommentByNameCustomer(nameCustomer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
    public List<Date> getYear(){
        return commentRepository.getYear();
    }
    public Integer getCommentByYearAndMonth(int year,int month){
        return commentRepository.getCommentByYearAndMonth(year,month);
    }
}
