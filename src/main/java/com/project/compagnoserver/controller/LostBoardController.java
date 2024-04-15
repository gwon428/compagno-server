package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.LostBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.LostBoardCommentService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/compagno/*")
public class LostBoardController {

    @Autowired
    private LostBoardService service;

    @Autowired
    private LostBoardCommentService comment;

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
    // 정렬 보기 -----------------------
    // 분실 날짜순(오래된순, 최신순) / 조회수순(높은순, 낮은순) / 작성일순(오래된순/최신순) / 저장순(최다저장/최소저장)
    @GetMapping("/lostBoard/search")
    public ResponseEntity<List<LostBoard>> viewBySearch(@RequestParam(name="page", defaultValue = "1")int page,
                                                        @RequestParam(name="lostAnimalKind", required = false)String lostAnimalKind,
                                                        @RequestParam(name="lostAnimalGender", required = false)String lostAnimalGender,
                                                        @RequestParam(name="userNickname", required = false) String userNickname,
                                                        @RequestParam(name="lostDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lostDate,
                                                        @RequestParam(name="lostLocation", required = false)String lostLocation,
                                                        @RequestParam(name="lostAnimalName", required = false)String lostAnimalName,
                                                        @RequestParam(name="sort", defaultValue = "0")int sortNum){

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


        Pageable pageable = PageRequest.of(page-1, 9);

        // 작성일 순(선입, 후입)
        Sort lRegiDate = Sort.by("lostRegiDate");
        Sort lRegiDateD = Sort.by("lostRegiDate").descending();

        if(sortNum==0){
            pageable = PageRequest.of(page-1, 9, lRegiDateD);
        }
        if(sortNum==1){
            pageable = PageRequest.of(page-1, 9, lRegiDate);
        }


        // 분실 날짜 순(선입, 후입)
        if(sortNum==2){
            Sort lDate = Sort.by("lostDate");
            pageable = PageRequest.of(page-1, 9, lDate);
        }
        if(sortNum==3){
            Sort lDateD = Sort.by("lostDate").descending();
            pageable = PageRequest.of(page-1, 9, lDateD);
        }

        // 조회수순(낮은 순, 높은 순)
        Sort lViewCount = Sort.by("lostViewCount");
        Sort lViewCountD = Sort.by("lostViewCount").descending();

        if(sortNum==4){
            pageable = PageRequest.of(page-1, 9, lViewCount);
        }
        if(sortNum==5){
            pageable = PageRequest.of(page-1, 9, lViewCountD);
        }

        Page<LostBoard> list = service.viewBySearch(pageable, builder);
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

            // 기존 사진 있을 경우 글만 변경 원하고 추가 사진 없으며 기존 사진 삭제 원할 때
            //if(!file.getOriginalFilename().equals("")) 해당 조건 사용하기!

        }
       // log.info("result : " + result.getLostTitle());
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    //[댓글 관련 로직]---------------------------------------------
    @PostMapping("/lostBoard/comment")
    public ResponseEntity createComment(@RequestBody LostBoardComment vo){
        // 시큐리티 담은 로그인한 사용자의 정보 가져오기
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        if(principal instanceof User){
            User user = (User) principal;
            vo.setUser(user);

            return ResponseEntity.ok().body(comment.create(vo));
        }
        return ResponseEntity.badRequest().build();
    }

    // 게시물 1개 따른 댓글 조회
    @GetMapping("/lostBoard/comment/{lostBoardCode}/comment")
    public ResponseEntity<List<LostBoardCommentDTO>> viewComment(@PathVariable(name="lostBoardCode")int lostBoardCode){
        List<LostBoardComment> topList = comment.topCommennts(lostBoardCode);
        List<LostBoardCommentDTO> response = commentDetailList(topList, lostBoardCode);
        return ResponseEntity.ok(response);
    }


    // 나머지 공통 부분 빼기
    public List<LostBoardCommentDTO> commentDetailList(List<LostBoardComment> comments, int lostBoardCode){
        List<LostBoardCommentDTO> response = new ArrayList<>();
        for(LostBoardComment item:comments){
            List<LostBoardComment> replies = comment.bottomComments(item.getLostParentCode(), lostBoardCode);
            List<LostBoardCommentDTO> repliesDTO = commentDetailList(replies, lostBoardCode);
            LostBoardCommentDTO dto = commentDetail(item);
            dto.setReplies(repliesDTO);
            response.add(dto);
        }
        return response;
    }

    // builder.build 공통부분 빼기
    public LostBoardCommentDTO commentDetail(LostBoardComment vo){

        return LostBoardCommentDTO.builder()
                .lostBoardCode(vo.getLostBoardCode())
                .userImg(vo.getUserImg())
                .userNickname(vo.getUserNickname())
                .commentDate(vo.getCommentDate())
                .commentContent(vo.getCommentContent())
                .lostBoardCode(vo.getLostBoardCode())
                .user(UserDTO.builder()
                        .userId(vo.getUser().getUserId())
                        .build())
                .build();
    }

}
