package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Parsing.Parsing;
import com.project.compagnoserver.domain.Parsing.QParsing;
import com.project.compagnoserver.service.ContentService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compagno/public/*")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class ContentController {

    @Autowired
    private ContentService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 전체보기
    @GetMapping("/content/list")
    public ResponseEntity<Page<Parsing>> view(@RequestParam(name="mainCate", required = true) int mainCate,
                                              @RequestParam(name="subCate", required = true, defaultValue = "0") int subCate,
                                              @RequestParam(name="mainReg", required = true, defaultValue = "0") int mainReg,
                                              @RequestParam(name="keyword", required = false) String keyword,
                                              @RequestParam(name="page", required = false, defaultValue = "1") int page){

        log.info("출력!!!!!!!!!");

        log.info("keyword : " + keyword);

        Sort sort = Sort.by(Sort.Order.asc("latitude"), Sort.Order.asc(("longtitude")));
        Pageable pageable = PageRequest.of(page-1, 30, sort);

        QParsing qParsing = QParsing.parsing;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;
        if(mainCate != 0){
            expression = qParsing.mainCateCode.eq(mainCate);
            builder.and(expression);
        }
        if(subCate != 0){
            expression = qParsing.subCateCode.eq(subCate);
            builder.and(expression);
        }
        if(mainReg != 0){
            expression = qParsing.mainregCode.eq(mainReg);
            builder.and(expression);
        }
        if(!StringUtils.isEmpty(keyword)){
            log.info("keyword!!!");
            expression = qParsing.name.like("%" + keyword.trim() + "%");
            builder.and(expression);
        }

        Page<Parsing> list = service.viewAll(builder, pageable);
        log.info("list : " + list);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 상세 페이지
    @GetMapping("/content/{code}")
    public ResponseEntity<Parsing> view(@PathVariable(name="code") int code){
        Parsing result = service.view(code);
        log.info("result : " + result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
