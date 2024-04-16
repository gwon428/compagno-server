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
import java.util.Objects;
import java.util.UUID;

@RestController
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
    public ResponseEntity<QnaQBoard> create(QnaQBoardDTO dto) throws IOException {

        Object principal = authentication();

        QnaQBoard vo = new QnaQBoard();

        if(principal instanceof User){

            User user = (User) principal;

            vo.setUserId(user.getUserId());
            vo.setUserNickname(user.getUserNickname());

            vo.setQnaQCode(dto.getQnaQCode());
            vo.setQnaQTitle(dto.getQnaQTitle());
            vo.setQnaQContent(dto.getQnaQContent());

            QnaQBoard result = service.create(vo);

            if(dto.getFiles()!= null) {
                for (MultipartFile file : dto.getFiles()) {
                    if(file.getOriginalFilename() != "") {
                        QnaQBoardImage img = new QnaQBoardImage();

                        String fileName = file.getOriginalFilename();
                        String uuid = UUID.randomUUID().toString();
                        String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

                        Path savePath = Paths.get(saveName);
                        file.transferTo(savePath);
                        img.setQnaQUrl(saveName);
                        img.setQnaQCode(result);
                        service.createImg(img);
                    }
                }
            }

            return result != null ? ResponseEntity.status(HttpStatus.CREATED).body(result) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.badRequest().build();
    }

    // 질문 목록 보기 (제목, 내용으로 검색 + 페이징처리)
    @GetMapping("/question")
    public ResponseEntity<List<QnaQBoard>> viewAll(@RequestParam(name="title", required = false) String title, @RequestParam(name="content", required = false) String content, @RequestParam (name="page", defaultValue = "1") int page){
        Sort sort = Sort.by("QnaQCode").descending();
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
        // 해당 게시판 수정 전
        QnaQBoard prev = service.view(dto.getQnaQCode());
        Object principal = authentication();

        QnaQBoard vo = new QnaQBoard();

        if(principal instanceof User) {

            User user = (User) principal;
            if(user.getUserId().equals(prev.getUserId())){
            vo.setUserId(user.getUserId());
            vo.setQnaQCode(dto.getQnaQCode());
            vo.setUserNickname(user.getUserNickname());
            vo.setQnaQTitle(dto.getQnaQTitle());
            vo.setQnaQContent(dto.getQnaQContent());

            log.info("파일비어잇어?" + (dto.getFiles().getFirst().getOriginalFilename() == ""));

            if (dto.getFiles().getFirst().getOriginalFilename() != "") {
                // 추가되는 사진이 있을 때
                if (prev.getFiles() != null) {
                    log.info("기존 사진 o, 추가 사진 o");
                    List<QnaQBoardImage> list = prev.getFiles();

                    for (QnaQBoardImage img : list) {
                        service.deleteImg(img.getQnaQImgCode());
                    }

                    for (MultipartFile file : dto.getFiles()) {
                        QnaQBoardImage image = new QnaQBoardImage();

                        String fileName = file.getOriginalFilename();
                        String uuid = UUID.randomUUID().toString();
                        String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

                        Path savePath = Paths.get(saveName);
                        file.transferTo(savePath);

                        image.setQnaQUrl(saveName);
                        image.setQnaQCode(prev);
                        service.createImg(image);
                    }
                } else {
                    log.info("기존 사진 x, 추가 사진 o");
                    for (MultipartFile file : dto.getFiles()) {
                        if (!Objects.equals(file.getOriginalFilename(), "")) {
                            QnaQBoardImage image = new QnaQBoardImage();

                            String fileName = file.getOriginalFilename();
                            String uuid = UUID.randomUUID().toString();
                            String saveName = uploadPath + File.separator + "QnaQ" + File.separator + uuid + "_" + fileName;

                            Path savePath = Paths.get(saveName);
                            file.transferTo(savePath);

                            image.setQnaQUrl(saveName);
                            image.setQnaQCode(vo);
                            service.createImg(image);
                        }
                    }
                }
            } else {
                // 추가되는 사진이 없을 때
                if (prev.getFiles().isEmpty()) {
                    log.info("기존 사진 x, 추가 사진 x");
                } else {
                    log.info("기존 사진 o, 추가 사진 x");
                    // 추가 사진은 없지만 삭제를 원하지 않을 때
                    List<QnaQBoardImage> list = prev.getFiles();

                    // 추가 사진 없이 삭제만 원할 때
//                List<QnaQBoardImage> list = prev.getFiles();
//                for(QnaQBoardImage img : list){
//                    service.deleteImg(img.getQnaQBoardImgCode());
//                }
                }
            }
            }
            QnaQBoard target = service.update(vo);
            return (target != null) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(target) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/question/{code}")
    public ResponseEntity<QnaQBoard> delete(@PathVariable(name="code") int code){
        QnaQBoard prev = service.view(code);
        Object principal = authentication();
        if(principal instanceof User) {

            User user = (User) principal;
            if (user.getUserId().equals(prev.getUserId()) || user.getUserRole().equals("ROLE_ADMIN")) {
                if (prev.getFiles() != null) {
                    QnaQBoard target = service.delete(code);
                    return (prev != null) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(prev) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        }
            return ResponseEntity.badRequest().build();
        }
}
