package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetFaq;
import com.project.compagnoserver.service.RegisterPetService;
import com.project.compagnoserver.service.XlsParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compagno/*")
public class RegisterPetController {

    @Autowired
    private RegisterPetService service;

    @Autowired
    private XlsParsingService xlsService;

    // 등록 대행기관 데이터
    @GetMapping("/upload")
    public ResponseEntity saveToDb() {
        xlsService.saveToDb();
        return ResponseEntity.ok().build();
    }

    // 대행기관 전체보기
    @GetMapping("/register-pet")
    public ResponseEntity<List<RegisterPet>> instList() {
        List<RegisterPet> list = service.instList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    // faq 등록
    @PostMapping("/faq")
    public ResponseEntity faqInsert(@RequestBody RegisterPetFaq faq) {
        service.faqInsert(faq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // faq 전체 보기
    @GetMapping("/faq")
    public ResponseEntity<List<RegisterPetFaq>> faqSelect() {
        List<RegisterPetFaq> list = service.faqSelect();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // faq 한 개 보기
    @GetMapping("/faq/{faqCode}")
    public ResponseEntity<RegisterPetFaq> faqSelect(@PathVariable("faqCode") int faqCode) {
        RegisterPetFaq faq = service.faqSelect(faqCode);
        return ResponseEntity.status(HttpStatus.OK).body(faq);
    }

    // faq 수정
    @PutMapping("/faq")
    public ResponseEntity faqUpdate(@RequestBody RegisterPetFaq faq) {
        service.faqUpdate(faq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // faq 삭제
    @DeleteMapping("/faq/{faqCode}")
    public ResponseEntity faqDelete(@PathVariable("faqCode") int faqCode) {
        service.faqDelete(faqCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    // faq 공개글만 조회
//    @GetMapping("/faq")
//    public ResponseEntity<List<RegisterPetFaq>> getPublicFaq() {
//        List<RegisterPetFaq> list = service.getPublicFaq();
//        return ResponseEntity.status(HttpStatus.OK).body(list);
//    }
}
