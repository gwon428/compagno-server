package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Parsing;
import com.project.compagnoserver.repo.ParsingDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class ContentService {

    @Autowired
    private ParsingDAO dao;

    public List<Parsing> findAll(){
        return dao.findAll();
    }
    public List<Parsing> findByMainCateCode(int code){
        return dao.findByMainCateCode(code);
    }

    public List<Parsing> findBySubCateCode(int code) {
        return dao.findBySubCateCode(code);
    }

    public List<Parsing> findByMainReg(int code) {
        return dao.findByMainReg(code);
    }

    public Optional<Parsing> findById(int code){
        return dao.findById(code);
    }

    public List<Parsing> findByMainCateReg(int code, int reg){
        return dao.findByMainCateReg(code, reg);
    }
}
