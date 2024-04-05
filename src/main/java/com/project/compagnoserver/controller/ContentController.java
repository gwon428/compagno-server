package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Parsing;
import com.project.compagnoserver.domain.ParsingDAO;
import com.project.compagnoserver.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
@Slf4j
public class ContentController {

    @Autowired
    private ContentService service;

    @GetMapping("/view/mainCate/{code}")
    public ResponseEntity<List<Parsing>> view(@PathVariable(name="code") int code){
        List<Parsing> list = service.findByMainCateCode(code);
        log.info("list : "  + list);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
