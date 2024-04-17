package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.SitterBoard.*;
import com.project.compagnoserver.service.SitterBoardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class SitterBoardController {

    @Autowired
    private SitterBoardService sitterBoardService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 전체 보기
    @GetMapping("/sitter")
    public ResponseEntity<List<SitterBoard>> sitterViewAll() {
        List<SitterBoard> sitterList = sitterBoardService.sitterViewAll();
        return ResponseEntity.status(HttpStatus.OK).body(sitterList);
    }

    // 상세 보기
    @GetMapping("/sitter/{code}")
    public ResponseEntity<SitterBoard> sitterView(@PathVariable("code") int code) {
        SitterBoard sitterBoard = sitterBoardService.sitterView(code);
        return ResponseEntity.status(HttpStatus.OK).body(sitterBoard);
    }

    // 글 등록
    @PostMapping("/sitter")
    public ResponseEntity<SitterBoard> sitterCreate(SitterBoardDTO sitterBoardDTO) throws IOException {
        SitterBoard sitter = new SitterBoard();
        SitterCategory sitterCategory = new SitterCategory();
        sitterCategory.setSitterCategoryCode(sitterBoardDTO.getSitterBoardCode());
        sitter.setSitterCategory(sitterCategory);
        sitter.setSitterLocation(sitterBoardDTO.getSitterLocation());
        sitter.setSitterTitle(sitterBoardDTO.getSitterTitle());
        sitter.setSitterContent(sitterBoardDTO.getSitterContent());

        SitterBoard sitterBoard = sitterBoardService.sitterCreate(sitter);

        // 이미지
        for(MultipartFile file : sitterBoardDTO.getFiles()) {
            log.info("file : " + file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + "sitterBoard" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            file.transferTo((savePath));

            SitterBoardImage sitterImg = new SitterBoardImage();
            sitterImg.setSitterImg(saveName);
            sitterImg.setSitterBoard(sitterBoard);
            sitterBoardService.sitterCreateImg(sitterImg);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 글 수정
    @PutMapping("/sitter")
    public ResponseEntity<SitterBoard> sitterUpdate(SitterBoard sitterBoard) {
        sitterBoardService.sitterUpdate(sitterBoard);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 글 삭제
    @DeleteMapping("/sitter/{code}")
    public ResponseEntity<SitterBoard> sitterDelete(@PathVariable("code") int code) {
        // 이미지 삭제
        List<SitterBoardImage> uploadedImg = sitterBoardService.sitterViewImg(code);
        SitterBoard contents = sitterBoardService.sitterView(code);
        for(SitterBoardImage image : uploadedImg) {
            File file = new File(image.getSitterImg());
            file.delete();
        }

        sitterBoardService.sitterDelete(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
