package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.QnaQ.QQnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardDTO;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.QnaQBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/compagno/*")
@Slf4j
public class QnaQBoardController {
    @Autowired
    private QnaQBoardService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public Object authentication(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getPrincipal();
    }
    // 질문 등록
    @PostMapping("/question")
    public ResponseEntity<QnaQBoardDTO> create(QnaQBoardDTO dto) throws IOException {

        QnaQBoard vo = new QnaQBoard();

            vo.setUserId(dto.getUserId());
            vo.setUserNickname(dto.getUserNickname());
            vo.setUserImg(dto.getUserImg());

            vo.setQnaQTitle(dto.getQnaQTitle());
            vo.setQnaQContent(dto.getQnaQContent());

            // 비밀글의 경우
            if (dto.getSecret() == null || dto.getSecret().equals("")) {
                dto.setSecret("");
            } else {
                dto.setSecret(dto.getSecret());
            }

            QnaQBoard result = service.create(QnaQBoard.builder()
                    .qnaQContent(dto.getQnaQContent())
                    .qnaQTitle(dto.getQnaQTitle())
                    .qnaQCode(dto.getQnaQCode())
                    .userNickname(dto.getUserNickname())
                    .userId(dto.getUserId())
                    .userImg(dto.getUserImg())
                    .secret(dto.getSecret())
                    .build());

            if (dto.getFiles() != null) {
                for (MultipartFile file : dto.getFiles()) {
                    if (file.getOriginalFilename() != "") {
                        QnaQBoardImage img = new QnaQBoardImage();

                        String fileName = file.getOriginalFilename();
                        String uuid = UUID.randomUUID().toString();
//                        uploadPath = "http:////192.168.10.28:8081";
                        String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

                        Path savePath = Paths.get(saveName);
                        file.transferTo(savePath);
//                        img.setQnaQUrl(saveName.substring(27));
                        img.setQnaQUrl(saveName.substring(30));
                        img.setQnaQCode(result.getQnaQCode());

                        service.createImg(img);
                    }
                }
            }
//        }
            return ResponseEntity.ok().build();
    }

    // 질문 목록 보기 (제목, 내용으로 검색 + 페이징처리)
    @GetMapping("/public/question")
    public ResponseEntity<Page<QnaQBoard>> viewAll(@RequestParam(name="title", required = false) String title,
                                                   @RequestParam(name="content", required = false) String content,
                                                   @RequestParam(name="id", required = false) String id,
                                                   @RequestParam (name="page", defaultValue = "1") int page){
        Sort sort = Sort.by("QnaQCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        QQnaQBoard qQnaQBoard = QQnaQBoard.qnaQBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;

        if(title != null){
            expression = qQnaQBoard.qnaQTitle.like("%" + title + "%");
            builder.and(expression);
        }
        if(id != null){
            expression = qQnaQBoard.userId.like("%"+id+"%");
            builder.and(expression);
        }
        if (content != null) {
            expression = qQnaQBoard.qnaQContent.like("%"+content+"%");
            builder.and(expression);
        }

        Page<QnaQBoard> list = service.viewAll(builder, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/question/manage")
    public ResponseEntity<List<QnaQBoard>> viewManage(@RequestParam (name="page", defaultValue = "1") int page){

        Object principal = authentication();

        QnaQBoard vo = new QnaQBoard();

        if(principal instanceof User) {

            User user = (User) principal;

            Sort sort = Sort.by("QnaQCode").descending();
            Pageable pageable = PageRequest.of(page - 1, 10, sort);

            QQnaQBoard qQnaQBoard = QQnaQBoard.qnaQBoard;
            BooleanBuilder builder = new BooleanBuilder();

            if (user.getUserRole().equals("ROLE_ADMIN")) {
                BooleanExpression expression = qQnaQBoard.qnaQStatus.eq("N");
                builder.and(expression);
            }

            Page<QnaQBoard> list = service.viewAll(builder, pageable);

            return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/question/mypage")
    public ResponseEntity<List<QnaQBoard>> viewMyQuestion(@RequestParam (name="page", defaultValue = "1") int page){

        Object principal = authentication();

        QnaQBoard vo = new QnaQBoard();

        if(principal instanceof User) {

            User user = (User) principal;

            Sort sort = Sort.by("QnaQCode").descending();
            Pageable pageable = PageRequest.of(page - 1, 10, sort);

            QQnaQBoard qQnaQBoard = QQnaQBoard.qnaQBoard;
            BooleanBuilder builder = new BooleanBuilder();

            if (user.getUserId().equals(user.getUserId())) {
                BooleanExpression expression = qQnaQBoard.userId.eq(user.getUserId());
                builder.and(expression);
            }

            Page<QnaQBoard> list = service.viewAll(builder, pageable);

            return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
        }
        return ResponseEntity.badRequest().build();
    }

    // 질문 상세보기(질문 코드 통해서)
    @GetMapping("/public/question/{code}")
    public ResponseEntity<QnaQBoardDTO> view(@PathVariable(name="code") int code){
        QnaQBoard result = service.view(code);
        QnaQBoardDTO dto = QnaQBoardDTO.builder()
                .qnaQCode(result.getQnaQCode())
                .qnaQTitle(result.getQnaQTitle())
                .qnaQContent(result.getQnaQContent())
                .qnaQStatus(result.getQnaQStatus())
                // 답변 달렸을때에만 update가 아닌 첫 생성 date가 들어가도록..
                .userId(result.getUserId())
                .userNickname(result.getUserNickname())
                .userImg(result.getUserImg())
                .images(service.viewImg(code))
                .build();

//                .qnaQDate(result.getQnaQDateUpdate())
        if(result.getQnaQDate() == null){
            dto.setQnaQDate(result.getQnaQDateUpdate());
        } else {
            dto.setQnaQDate(result.getQnaQDate());
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // 질문 수정
    @PutMapping("/question")
    public ResponseEntity update(QnaQBoardDTO dto) throws IOException {

        if (dto.getImages() != null) {
                List<String> imagesList = dto.getImages()
                        .stream().map(image -> image.getQnaQUrl()).collect(Collectors.toList());

                List<QnaQBoardImage> list = service.viewImg(dto.getQnaQCode());

                // 이전 이미지 삭제
                for (QnaQBoardImage image : list) {

                    if ((dto.getImages() != null && !imagesList.contains(image.getQnaQUrl())) || (dto.getImages() == null)) {
                    File file = new File(image.getQnaQUrl());
                    file.delete();

                        service.deleteImg(image.getQnaQImgCode());
                    }
                }
            } else {
            List<QnaQBoardImage> list = service.viewImg(dto.getQnaQCode());
            for (QnaQBoardImage image : list) {
                    File file = new File(image.getQnaQUrl());
                    file.delete();

                    service.deleteImg(image.getQnaQImgCode());
                }
            }


            if (dto.getFiles() != null) {
                for (MultipartFile file : dto.getFiles()) {
                    QnaQBoardImage img = new QnaQBoardImage();

                    String fileName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

//                    img.setQnaQUrl(saveName.substring(27));
                    img.setQnaQUrl(saveName.substring(30));
                    img.setQnaQCode(dto.getQnaQCode());


                    service.createImg(img);
                }
            } else {

            }


            // 추가하는 이미지가 비어있지 않을 때
            QnaQBoard vo = QnaQBoard.builder()
                    .userId(dto.getUserId())
                    .userNickname(dto.getUserNickname())
                    .userImg(dto.getUserImg())
                    .qnaQCode(dto.getQnaQCode())
                    .qnaQTitle(dto.getQnaQTitle())
                    .qnaQContent(dto.getQnaQContent())
                    .qnaQStatus("N")
                    .build();
            service.update(vo);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/{code}")
    public ResponseEntity<QnaQBoardDTO> delete(@PathVariable(name="code") int code){
        List<QnaQBoardImage> list = service.viewImg(code);

        if(list != null){
            for(QnaQBoardImage item : list){
                File file = new File(item.getQnaQUrl());
                file.delete();

                service.deleteImg(item.getQnaQImgCode());
            }

        }

        service.delete(code);
//        QnaQBoardDTO dto = QnaQBoardDTO.builder()
//                .qnaQCode(result.getQnaQCode())
//                .qnaQTitle(result.getQnaQTitle())
//                .qnaQContent(result.getQnaQContent())
//                .userId(result.getUserId())
//                .userNickname(result.getUserNickname())
//                .images(service.viewImg(code))
//                .build();

//        Object principal = authentication();

//        if(principal instanceof User) {
//
//            User user = (User) principal;
//            if (user.getUserId().equals(dto.getUserId()) || user.getUserRole().equals("ROLE_ADMIN")) {
//                if(dto.getImages()!= null){
////                if (prev.getImages() != null) {
//                    QnaQBoard target = service.delete(code);
//                    return (dto != null) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(dto) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                }
//            }
//        }
//        if(dto.getImages()!= null){
////                if (prev.getImages() != null) {
//            QnaQBoard target = service.delete(code);
//            return (dto != null) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(dto) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

