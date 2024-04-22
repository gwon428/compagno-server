package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.service.OneDayClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/*")
public class OneDayClassController {

    @Autowired
    private OneDayClassService service;

    // 파일 업로드 관련 로직 !
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath; // D:\\upload   // 파일 업로드 관련 위치!

    // 클래스 등록
    @PostMapping("/ClassBoard")
    public ResponseEntity insert(@RequestBody ClassBoard vo){

        // 파일 업로드

        service.insert(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 클래스 전체 보기
    @GetMapping("/ClassBoard")
    public ResponseEntity <List<ClassBoard>> viewAll(){
       List<ClassBoard> list = service.viewAll();
       return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 클래스 한개 보기
    @GetMapping("/ClassBoard/{odcCode}")
    public ResponseEntity view(@PathVariable("odcCode") int odcCode){
        ClassBoard vo = service.view(odcCode);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    // 등록된 클래스 수정
    @PutMapping("/ClassBoard")
    public ResponseEntity update(@RequestBody ClassBoard vo){
        service.update(vo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 등록된 클래스 삭제
    @DeleteMapping("/ClassBoard/{odcCode}")
    public ResponseEntity delete(@PathVariable("odcCode") int odcCode){
        service.delete(odcCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
