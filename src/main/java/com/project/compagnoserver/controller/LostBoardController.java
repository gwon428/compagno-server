package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.LostBoard;
import com.project.compagnoserver.domain.LostBoardDTO;
import com.project.compagnoserver.domain.LostBoardImage;
import com.project.compagnoserver.service.LostBoardService;

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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/compagno/*")
public class LostBoardController {

    @Autowired
    private LostBoardService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 추가 ----------------------------------------
    @PostMapping("/lostBoard")
    public ResponseEntity<LostBoard> create(LostBoardDTO dto) throws IOException {
        LostBoard lost = new LostBoard();
        lost.setUserId(dto.getUserId());
        lost.setUserImg(dto.getUserImg());
        lost.setUserPhone(dto.getUserPhone());
        lost.setLostTitle(dto.getLostTitle());
        lost.setLostAnimalImage(dto.getLostAnimalImage());
        lost.setLostAnimalName(dto.getLostAnimalName());
        lost.setLostDate(dto.getLostDate());
        lost.setLostLocation(dto.getLostLocation());
        lost.setLostAnimalKind(dto.getLostAnimalKind());
        lost.setLostAnimalColor(dto.getLostAnimalColor());
        lost.setLostAnimalGender(dto.getLostAnimalGender());
        lost.setLostAnimalAge(dto.getLostAnimalAge());
        lost.setLostAnimalFeature(dto.getLostAnimalFeature());
        lost.setLostAnimalRFID(dto.getLostAnimalRFID());

        LostBoard result = service.create(lost);
        if(dto.getImages()!=null){
            for(MultipartFile file : dto.getImages()){
                LostBoardImage images = new LostBoardImage();

                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "lostBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                images.setLostImage(saveName);
                images.setLostBoardCode(result);
                service.createImages(images);
            }
        }
        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 전체 보기 ------------------------------------
    @GetMapping("/lostBoard")
    public ResponseEntity<List<LostBoard>> viewAll(@RequestParam(name="page", defaultValue = "1")int page){
        Sort sort = Sort.by("lostBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 9);
        Page<LostBoard> list = service.viewAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
    }


    // 하나 보기 --------------------------------------
    @GetMapping("/lostBoard/{lostBoardCode}")
    public ResponseEntity<LostBoard> view(@PathVariable(name="lostBoardCode")int lostBoardCode){
        LostBoard lost = service.view(lostBoardCode);
        return ResponseEntity.status(HttpStatus.OK).body(lost);
    }


    // 수정 ----------------------------------------
    @PutMapping("/lostBoard")
    public ResponseEntity<LostBoard> update(LostBoardDTO dto) throws IOException {
        LostBoard lost = new LostBoard();
        lost.setUserId(dto.getUserId());
        lost.setUserImg(dto.getUserImg());
        lost.setUserPhone(dto.getUserPhone());
        lost.setLostTitle(dto.getLostTitle());
        lost.setLostAnimalImage(dto.getLostAnimalImage());
        lost.setLostAnimalName(dto.getLostAnimalName());
        lost.setLostDate(dto.getLostDate());
        lost.setLostLocation(dto.getLostLocation());
        lost.setLostAnimalKind(dto.getLostAnimalKind());
        lost.setLostAnimalColor(dto.getLostAnimalColor());
        lost.setLostAnimalGender(dto.getLostAnimalGender());
        lost.setLostAnimalAge(dto.getLostAnimalAge());
        lost.setLostAnimalFeature(dto.getLostAnimalFeature());
        lost.setLostAnimalRFID(dto.getLostAnimalRFID());

        //LostBoard prev = service.view(dto.getLostBoardCode());
        //LostBoard result = service.create(lost);
        LostBoard result = service.update(lost);
        if(dto.getImages()!=null){
            for(MultipartFile file : dto.getImages()){
                LostBoardImage images = new LostBoardImage();

                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "lostBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                images.setLostImage(saveName);
                images.setLostBoardCode(result);
                service.update(images);
            }
        }
        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 삭제 ------------------------------------------
    @DeleteMapping("/lostBoard/{lostBoardCode}")
    public ResponseEntity<LostBoard> delete(@PathVariable(name="lostBoardCode")int lostBoardCode){

        LostBoard lost = service.view(lostBoardCode);
        if(lost!=null){
            service.delete(lostBoardCode);
        }
        return (lost!=null)?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(lost):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
