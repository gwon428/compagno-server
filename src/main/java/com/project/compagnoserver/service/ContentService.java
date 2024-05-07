package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Parsing.Parsing;
import com.project.compagnoserver.repo.Parsing.ParsingDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service @Slf4j
public class ContentService {

    @Autowired
    private ParsingDAO dao;

    @Autowired
    private JPAQueryFactory queryFactory;

    public Page<Parsing> viewAll(BooleanBuilder builder, Pageable pageable){
            return dao.findAll(builder, pageable);
    }

public Parsing view(int code){
        return dao.findById(code).orElse(null);
}
}
