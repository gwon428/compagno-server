package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Parsing;
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
import java.util.Optional;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
@Slf4j
public class ContentController {

    @Autowired
    private ContentService service;

    // 전체보기
    @GetMapping("/view")
    public ResponseEntity<List<Parsing>> view(){
        List<Parsing> list = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 메인카테고리별
    @GetMapping("/view/mainCate/{code}")
    public ResponseEntity<List<Parsing>> viewMain(@PathVariable(name="code") int code){
        List<Parsing> list = service.findByMainCateCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 서브카테고리별
    @GetMapping("/view/subCate/{code}")
    public ResponseEntity<List<Parsing>> viewSub(@PathVariable(name="code") int code){
        List<Parsing> list = service.findBySubCateCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //지역별
    @GetMapping("/view/mainReg/{code}")
    public ResponseEntity<List<Parsing>> viewreg(@PathVariable(name="code")int code){
        List<Parsing> list = service.findByMainReg(code);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 한개씩 보기(상세정보)
    @GetMapping("/view/num/{code}")
    public ResponseEntity<Optional<Parsing>> view(@PathVariable(name="code") int code){
        Optional<Parsing> vo = service.findById(code);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    @GetMapping("/view/{code}/{reg}")
    public ResponseEntity<List<Parsing>> viewMainReg(@PathVariable(name="code") int code, @PathVariable(name="reg") int reg){
        List<Parsing> list = service.findByMainCateReg(code, reg);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
