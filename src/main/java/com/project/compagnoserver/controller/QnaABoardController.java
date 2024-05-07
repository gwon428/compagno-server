package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.QnaA.QnaABoard;
import com.project.compagnoserver.domain.QnaA.QnaABoardDTO;
import com.project.compagnoserver.domain.QnaA.QnaABoardImage;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.QnaABoardService;
import com.project.compagnoserver.service.QnaQBoardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/compagno/*")
@Slf4j
public class QnaABoardController {
    @Autowired
    private QnaABoardService service;

    @Autowired
    private QnaQBoardService questionService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public Object authentication(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getPrincipal();
    }

    @GetMapping("/public/question/{code}/answer")
    public ResponseEntity<QnaABoardDTO> select(@PathVariable(name="code") int code){
        QnaABoard vo = service.view(code);

        if(vo != null){
            QnaABoardDTO dto = QnaABoardDTO.builder()
                    .qnaQCode(vo.getQnaQCode().getQnaQCode())
                    .qnaACode(vo.getQnaACode())
                    .qnaATitle(vo.getQnaATitle())
                    .qnaAContent(vo.getQnaAContent())
                    .qnaADate(vo.getQnaADateUpdate())
                    .userId(vo.getUserId())
                    .images(service.viewImg(vo.getQnaACode()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else {
            log.info("답변이 없엉ㅇ");
            return ResponseEntity.status(HttpStatus.OK).build();
        }


    }
    // 답변 등록
    @PostMapping("/answer")
    public ResponseEntity<QnaABoard> create (QnaABoardDTO dto) throws IOException {
//        Object principal = authentication();

        QnaABoard vo = new QnaABoard();
        log.info("등록?");

//        if(principal instanceof User) {
//            User user = (User) principal;
//            String status = questionService.view(dto.getQnaQCode()).getQnaQStatus();
//            log.info("status : " + status);
//            log.info("equals : " + status.equals("N"));
//            if(user.getUserRole().equals("ROLE_ADMIN") && status.equals("N")){

            vo.setUserId(dto.getUserId());
            vo.setQnaACode(dto.getQnaACode());
            vo.setQnaQCode(QnaQBoard.builder().qnaQCode(dto.getQnaQCode()).build());
            vo.setQnaATitle(dto.getQnaATitle());
            vo.setQnaAContent(dto.getQnaAContent());

            QnaABoard result = service.create(vo);
            // 답변 등록 시 status 업데이트
            QnaQBoard update = questionService.view(dto.getQnaQCode());
            update.setQnaACode(result);
            update.setQnaQStatus("Y");
            // 수정 때문에..
            update.setQnaQDateUpdate(update.getQnaQDate());
            log.info("date : " + update.getQnaQDate());
            log.info("상태 업데이트했어요");

            questionService.update(update);

            if (dto.getFiles() != null) {
                for (MultipartFile file : dto.getFiles()) {
                    if (file.getOriginalFilename() != "") {
                        QnaABoardImage img = new QnaABoardImage();

                        String fileName = file.getOriginalFilename();
                        String uuid = UUID.randomUUID().toString();
//                        uploadPath = "http:////192.168.10.28:8081";
                        String saveName = uploadPath + File.separator + "QnaA" + File.separator + uuid + "_" + fileName;

                        Path savePath = Paths.get(saveName);
                        file.transferTo(savePath);
                        img.setQnaAUrl(saveName.substring(27));
                        img.setQnaACode(result.getQnaACode());
                        service.createImg(img);
                    }
                }
            }
//            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        log.info("유저에걸려잇서?");
        return ResponseEntity.ok().build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/answer")
    public ResponseEntity<QnaABoard> update(QnaABoardDTO dto) throws IOException {
       log.info("dto : " + dto);
        if(dto.getImages() != null){
            List<String> imagesList = dto.getImages()
                    .stream().map(image -> image.getQnaAUrl()).collect(Collectors.toList());
            log.info("imagesList : " + imagesList);

            List<QnaABoardImage> list = service.viewImg(dto.getQnaACode());
            log.info("list :  " + list);

            for (QnaABoardImage image : list){
                if((dto.getImages() != null && !imagesList.contains(image.getQnaAUrl())) || (dto.getImages() == null)){
                    File file = new File(image.getQnaAUrl());
                    file.delete();

                    service.deleteImg(image.getQnaAImgCode());
                }
            }

        }

        if(dto.getFiles() != null){
            for(MultipartFile file : dto.getFiles()) {
                QnaABoardImage img = new QnaABoardImage();

                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "QnaA" + File.separator + uuid + "_" + fileName;

                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                img.setQnaAUrl(saveName.substring(27));
                img.setQnaACode(dto.getQnaACode());

                service.createImg(img);
            }

        } else {
            log.info("추가하는 이미지 없음");
        }

        QnaABoard vo = QnaABoard.builder()
                .userId(dto.getUserId())
                .qnaQCode(QnaQBoard.builder().qnaQCode(dto.getQnaQCode()).build())
                .qnaACode(dto.getQnaACode())
                .qnaATitle(dto.getQnaATitle())
                .qnaAContent(dto.getQnaAContent())
                .qnaADate((Timestamp) dto.getQnaADate())
                .build();

        service.update(vo);

        return ResponseEntity.ok().build();
    }

    // 답변 삭제
    @DeleteMapping("/answer/{code}")
    public ResponseEntity<QnaABoard> delete(@PathVariable(name="code") int code){
        List<QnaABoardImage> list = service.viewImg(code);

        if(list != null){
            for(QnaABoardImage item : list){
                File file = new File(item.getQnaAUrl());
                file.delete();

                service.deleteImg(item.getQnaAImgCode());
            }
        }

        QnaABoard target = service.delete(code);

        QnaQBoard update = questionService.view(target.getQnaQCode().getQnaQCode());
        update.setQnaQStatus("N");
        questionService.update(update);


//        Object principal = authentication();

//        QnaABoard vo = new QnaABoard();

//        if(principal instanceof User) {
//
//                if (prev.getFiles() != null) {
//                    QnaABoard target = service.delete(code);
//
//                    QnaQBoard update = questionService.view(target.getQnaQCode());
//                    update.setQnaQStatus("N");
//                    questionService.update(update);
//                }
//        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
