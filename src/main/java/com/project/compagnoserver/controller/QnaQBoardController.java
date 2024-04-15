package com.project.compagnoserver.controller;


import com.project.compagnoserver.domain.QnaQ.QQnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardDTO;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/qna/*")
@Slf4j
public class QnaQBoardController {
    @Autowired
    private QnaQBoardService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 질문 등록
    @PostMapping("/question")
    public ResponseEntity<QnaQBoard> create(QnaQBoardDTO dto) throws IOException {
        QnaQBoard vo = new QnaQBoard();

        vo.setQnaQBoardCode(dto.getQnaQBoardCode());
        vo.setUserId(dto.getUserId());
        vo.setUserNickname(dto.getUserNickname());
        vo.setQnaQTitle(dto.getQnaQTitle());
        vo.setQnaQContent(dto.getQnaQContent());

        QnaQBoard result = service.create(vo);

        for(MultipartFile file : dto.getFiles()){
            QnaQBoardImage img = new QnaQBoardImage();

            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);
            file.transferTo(savePath);
            img.setQnaQBoardUrl(saveName);
            img.setQnaQBoardCode(result);
            service.createImg(img);
        }
        return result != null ? ResponseEntity.status(HttpStatus.CREATED).body(result) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 질문 목록 보기 (제목, 내용으로 검색 + 페이징처리)
    @GetMapping("/question")
    public ResponseEntity<List<QnaQBoard>> viewAll(@RequestParam(name="title", required = false) String title, @RequestParam(name="content", required = false) String content, @RequestParam (name="page", defaultValue = "1") int page){
        Sort sort = Sort.by("QnaQBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);


        QQnaQBoard qQnaQBoard = QQnaQBoard.qnaQBoard;
        BooleanBuilder builder = new BooleanBuilder();

        if(!StringUtils.isEmpty(title)){
         BooleanExpression expression = qQnaQBoard.qnaQTitle.contains(title);
           builder.and(expression);
        } else if (!StringUtils.isEmpty(content)){
            BooleanExpression expression = qQnaQBoard.qnaQContent.contains(content);
           builder.and(expression);
        }

        Page<QnaQBoard> list = service.viewAll(builder, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
    }

    // 질문 상세보기(질문 코드 통해서)
    @GetMapping("/question/{code}")
    public ResponseEntity<QnaQBoard> view(@PathVariable(name="code") int code){
        QnaQBoard result = service.view(code);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 질문 수정
    @PutMapping("/question")
    public ResponseEntity<QnaQBoard> update(QnaQBoardDTO dto) throws IOException {
        QnaQBoard vo = new QnaQBoard();
        vo.setQnaQBoardCode(dto.getQnaQBoardCode());
        vo.setUserNickname(dto.getUserNickname());
        vo.setQnaQTitle(dto.getQnaQTitle());
        vo.setQnaQContent(dto.getQnaQContent());

        // 해당 게시판 수정 전 file 리스트
        List<QnaQBoardImage> prev = service.view(dto.getQnaQBoardCode()).getFiles();

        if(prev == null){
            // 기존 사진이 없고, 추가할 사진이 있는 경우
            if(!dto.getFiles().isEmpty()){
                for(MultipartFile file : dto.getFiles()) {
                    QnaQBoardImage image = new QnaQBoardImage();

                    String fileName = dto.getFiles().toString();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

                    image.setQnaQBoardUrl(saveName);
//                    image.setQnaQBoardCode(vo);

                    log.info("service + 기존 사진 x, 추가 사진 o - image : " + image);
                    service.createImg(image);
                }
            }
        } else {
            if(dto.getFiles().isEmpty()){
                // 기존 사진이 있고, 추가하는 사진이 없는 경우
                vo.setFiles(prev);
            } else if(!dto.getFiles().isEmpty()) {
                // 기존 사진 있고, 추가할 사진이 있는 경우
                for(MultipartFile file : dto.getFiles()){
                    QnaQBoardImage image = new QnaQBoardImage();

                    String fileName = dto.getFiles().toString();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

                    image.setQnaQBoardUrl(saveName);
                    image.setQnaQBoardCode(vo);
                }

            } QnaQBoard target = service.update(vo);
            return (target != null) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(target) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return null;
    }
}
