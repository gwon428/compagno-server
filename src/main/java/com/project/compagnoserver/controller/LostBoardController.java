package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.LostBoard.LostBoard;
import com.project.compagnoserver.domain.LostBoard.LostBoardDTO;
import com.project.compagnoserver.domain.LostBoard.LostBoardImage;
import com.project.compagnoserver.domain.LostBoard.QLostBoard;
import com.project.compagnoserver.service.LostBoardService;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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

    // 추가 --------------------------------------------------------------------
    @PostMapping("/lostBoard")
    public ResponseEntity<LostBoard> create(LostBoardDTO dto) throws IOException {

        LostBoard lost = new LostBoard();
        lost.setUserId(dto.getUserId());
        lost.setUserImg(dto.getUserImg());
        lost.setUserNickname(dto.getUserNickname());
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
            log.info("getImages : " + dto.getImages());
            for(MultipartFile file : dto.getImages()){
                if(!file.getOriginalFilename().equals("")){
                    log.info("originName: " + file.getOriginalFilename());
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
        }
        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 보기------------------------------------------------------------------------------
    // 전체 보기 -------------------
    @GetMapping("/lostBoard")
    public ResponseEntity<List<LostBoard>> viewAll(@RequestParam(name="page", defaultValue = "1")int page){
        Sort sort = Sort.by("lostBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 9, sort);
        Page<LostBoard> list = service.viewAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
    }

    // 하나 보기 --------------------
    @GetMapping("/lostBoard/{lostBoardCode}")
    public ResponseEntity<LostBoard> view(@PathVariable(name="lostBoardCode")int lostBoardCode){
        LostBoard lost = service.view(lostBoardCode);
        return ResponseEntity.status(HttpStatus.OK).body(lost);
    }

    // 검색 보기 -----------------------
    // 종류(전체, 강아지, 고양이, 그외) / 성별 / 작성자 닉네임 / 분실 날짜 / 분실 지역 / 분실 동물 이름
    // lostAnimalKind / lostAnimalGender / userNickname / lostDate / lostLocation / lostAnimalName
    @GetMapping("/lostBoard/search")
    public ResponseEntity<List<LostBoard>> viewBySearch(@RequestParam(name="page", defaultValue = "1")int page,
                                                        @RequestParam(name="lostAnimalKind", required = false)String lostAnimalKind,
                                                        @RequestParam(name="lostAnimalGender", required = false)String lostAnimalGender,
                                                        @RequestParam(name="userNickname", required = false) String userNickname,
                                                        @RequestParam(name="lostDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lostDate,
                                                        @RequestParam(name="lostLocation", required = false)String lostLocation,
                                                        @RequestParam(name="lostAnimalName", required = false)String lostAnimalName){
        Sort sort = Sort.by("lostBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 9, sort);

        QLostBoard qLostBoard = QLostBoard.lostBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = null;
        if(lostAnimalKind != null){
            expression = qLostBoard.lostAnimalKind.contains(lostAnimalKind);
            builder.and(expression);
        }
        if(lostAnimalGender != null){
            expression = qLostBoard.lostAnimalGender.contains(lostAnimalGender);
            builder.and(expression);
        }
        if(userNickname != null){
            expression = qLostBoard.userNickname.contains(userNickname);
            builder.and(expression);
        }
        if(lostDate != null){
            expression = qLostBoard.lostDate.eq(lostDate);
            builder.and(expression);
        }
        if(lostLocation != null){
            expression = qLostBoard.lostLocation.contains(lostLocation);
            builder.and(expression);
        }
        if(lostAnimalName != null){
            expression = qLostBoard.lostAnimalName.contains(lostAnimalName);
            builder.and(expression);
        }

        Page<LostBoard> list = service.viewBySearch(pageable, builder);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
    }

    // 정렬 보기 -----------------------
    // 그 때의 sort가 무엇인지에 따라 다 다르게 GetMapping을 만들어야 하나?
    // 분실 날짜순(오래된순, 최신순) / 조회수순(높은순, 낮은순) / 작성일순(오래된순/최신순) / 저장순(최다저장/최소저장)
    @GetMapping("/lostBoard/sort")
    public ResponseEntity<List<LostBoard>> viewBySort(@RequestParam(name="page", defaultValue = "1")int page){

        // 분실 날짜 순(선입, 후입)
        Sort lDate = Sort.by("lostDate");
        Sort lDateD = Sort.by("lostDate").descending();

        Pageable pageable = null;

        pageable = PageRequest.of(page-1, 9, lDate);
        pageable = PageRequest.of(page-1, 9, lDateD);

        // 조회수순(낮은 순, 높은 순)
        Sort lViewCount = Sort.by("lostViewCount");
        Sort lViewCountD = Sort.by("lostViewCount").descending();

        pageable = PageRequest.of(page-1, 9, lViewCount);
        pageable = PageRequest.of(page-1, 9, lViewCountD);

        // 작성일 순(선입, 후입)
        Sort lRegiDate = Sort.by("lostRegiDate");
        Sort lRegiDateD = Sort.by("lostRegiDate").descending();

        pageable = PageRequest.of(page-1, 9, lRegiDate);
        pageable = PageRequest.of(page-1, 9, lRegiDateD);

        // 저장(찜, 하트) (낮은 순, 높은 순) -> 컬럼 추가 필요

        Page<LostBoard> list = service.viewBySort(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
    }



    // 수정 ---------------------------------------------------------------------------------------
    @PutMapping("/lostBoard")
    public ResponseEntity<LostBoard> update(LostBoardDTO dto) throws IOException {
        log.info("dto : " + dto);

        LostBoard prev = service.view(dto.getLostBoardCode());
        LostBoard lost = service.view(dto.getLostBoardCode());
//        LostBoard lost = new LostBoard();
        lost.setUserId(dto.getUserId());
        lost.setUserImg(dto.getUserImg());
        lost.setUserNickname(dto.getUserNickname());
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

//        LostBoard prev = service.view(dto.getLostBoardCode());
        //LostBoard result = service.create(lost);

        log.info("prev.getLostTitle : " + prev.getLostTitle());

        LostBoard result = service.update(lost);
        log.info("lost.getLostTitle : " + lost.getLostTitle());
        log.info("result.getLostTitle : " + result.getLostTitle());
        if(dto.getImages()!=null){
            // 1) 추가 사진 O
            if(prev.getImages()!=null){
                // -> 기존 사진 O : 기존 사진 삭제 + 추가 사진 넣기
                service.imageDelete(dto.getLostBoardCode());
            } else{// -> 기존 사진 X : 추가 사진 넣기
            }
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

        } else {// 2) 추가 사진 X
            // -> 기존 사진 O : 기존 사진 남기기 (사진 처리X)
            // -> 기존 사진 x : 사진 처리X
            if(prev.getImages()!=null){} else {}

        }
       // log.info("result : " + result.getLostTitle());
        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    // 기존 사진 있을 경우 글만 변경 원하고 추가 사진 없으며 기존 사진 삭제 원할 때
    //if(!file.getOriginalFilename().equals("")) 해당 조건 사용하기!


    // 삭제 ------------------------------------------
    @DeleteMapping("/lostBoard/{lostBoardCode}")
    public ResponseEntity<LostBoard> delete(@PathVariable(name="lostBoardCode")int lostBoardCode){

        LostBoard lost = service.view(lostBoardCode);
        if(lost!=null){
            service.delete(lostBoardCode);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }


}
