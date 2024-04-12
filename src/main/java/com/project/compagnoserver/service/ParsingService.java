package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Parsing.Parsing;
import com.project.compagnoserver.repo.Parsing.ParsingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParsingService {
    @Autowired
    private ParsingDAO dao;
    public Parsing create(Parsing vo){
        return dao.save(vo);
    }

}