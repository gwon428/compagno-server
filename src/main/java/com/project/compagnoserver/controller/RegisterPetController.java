package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.project.compagnoserver.service.RegisterPetService;
import com.project.compagnoserver.service.XlsParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class RegisterPetController {

    @Autowired
    private RegisterPetService service;

    @Autowired
    private XlsParsingService xlsService;

    @GetMapping("/upload")
    public ResponseEntity saveToDb() {
        xlsService.saveToDb();
        return ResponseEntity.ok().build();
    }

    // 전체 보기
    @GetMapping("/register-pet")
    public ResponseEntity<List<RegisterPet>> select() {
        List<RegisterPet> list = service.select();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
