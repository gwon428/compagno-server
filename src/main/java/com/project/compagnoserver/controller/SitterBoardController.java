package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
import com.project.compagnoserver.domain.SitterBoard.SitterBoard;
import com.project.compagnoserver.domain.SitterBoard.SitterBoardDTO;
import com.project.compagnoserver.domain.SitterBoard.SitterBoardImage;
import com.project.compagnoserver.service.SitterBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/compagno/*")
public class SitterBoardController {

    @Autowired
    private SitterBoardService sitterService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 등록
    @PostMapping("/sitter")
    public ResponseEntity<SitterBoard> sitterCreate(SitterBoardDTO sitterDTO) {
        SitterBoard sitter = new SitterBoard();

        sitter.setSitterBoardCode(sitterDTO.getSitterBoardCode());
        sitter.setSitterCategory(sitterDTO.getSitterCategory());
        sitter.setSitterLocation(sitterDTO.getSitterLocation());
        sitter.setSitterTitle(sitterDTO.getSitterTitle());
        sitter.setSitterContent(sitterDTO.getSitterContent());
        sitter.setUserId(sitterDTO.getUserId());
    }
}
