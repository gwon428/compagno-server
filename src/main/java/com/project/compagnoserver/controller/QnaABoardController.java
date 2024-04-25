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
import java.util.List;
import java.util.UUID;

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
        log.info("code : " + code);
        QnaABoard vo = service.view(code);

        if(vo != null){
            log.info("vo : " + vo);

            QnaABoardDTO dto = new QnaABoardDTO();
            dto.setQnaACode(vo.getQnaACode());
            dto.setQnaATitle(vo.getQnaATitle());
            dto.setQnaQCode(vo.getQnaQCode());
            dto.setQnaAContent(vo.getQnaAContent());
            log.info("dto : " + dto);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else {
            log.info("답변이 없엉ㅇ");
            return ResponseEntity.status(HttpStatus.OK).build();
        }


    }
    // 답변 등록
    @PostMapping("/answer")
    public ResponseEntity<QnaABoard> create (QnaABoardDTO dto) throws IOException {
        Object principal = authentication();

        QnaABoard vo = new QnaABoard();
        log.info("등록?");

        if(principal instanceof User) {
            User user = (User) principal;
            String status = questionService.view(dto.getQnaQCode()).getQnaQStatus();
            log.info("status : " + status);
            log.info("equals : " + status.equals("N"));
            if(user.getUserRole().equals("ROLE_ADMIN") && status.equals("N")){

            vo.setQnaACode(dto.getQnaACode());
            vo.setQnaQCode(dto.getQnaQCode());
            vo.setUserId(user.getUserId());
            vo.setQnaATitle(dto.getQnaATitle());
            vo.setQnaAContent(dto.getQnaAContent());

            QnaABoard result = service.create(vo);
            // 답변 등록 시 status 업데이트
            QnaQBoard update = questionService.view(dto.getQnaQCode());
            update.setQnaACode(result);
            update.setQnaQStatus("Y");

            questionService.update(update);

            if (dto.getFiles() != null) {
                for (MultipartFile file : dto.getFiles()) {
                    if (file.getOriginalFilename() != "") {
                        QnaABoardImage img = new QnaABoardImage();

                        String fileName = file.getOriginalFilename();
                        String uuid = UUID.randomUUID().toString();
                        String saveName = uploadPath + File.separator + "QnaA" + File.separator + uuid + "_" + fileName;

                        Path savePath = Paths.get(saveName);
                        file.transferTo(savePath);
                        img.setQnaAUrl(saveName);
                        img.setQnaACode(result);
                        service.createImg(img);
                    }
                }
            }
//            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            }
            log.info("야!");
        }
        
        log.info("유저에걸려잇서?");
        return vo!=null ?
                ResponseEntity.status(HttpStatus.CREATED).body(vo) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/answer")
    public ResponseEntity<QnaABoard> update(QnaABoardDTO dto) throws IOException {
        // 수정 전
        QnaABoard prev = service.view(dto.getQnaACode());

        Object principal = authentication();

        QnaABoard vo = new QnaABoard();

        if(principal instanceof User) {
            User user = (User) principal;

            if(user.getUserRole().equals("ROLE_ADMIN") && prev.getUserId().equals(user.getUserId())){

            vo.setQnaACode(dto.getQnaACode());
            vo.setQnaQCode(dto.getQnaQCode());
            vo.setUserId(user.getUserId());
            vo.setQnaATitle(dto.getQnaATitle());
            vo.setQnaAContent(dto.getQnaAContent());

        log.info("파일 확인 " + dto.getFiles().getFirst().getOriginalFilename());

        if(dto.getFiles().getFirst().getOriginalFilename() != ""){
            if(prev.getFiles() != null){
                log.info("기존 사진 o, 추가 사진 o");
                List<QnaABoardImage> list = prev.getFiles();

                // 기존 사진 삭제
                for(QnaABoardImage img : list){
                   service.deleteImg(img.getQnaAImgCode());
                }

                // 후, 추가되는 사진 다시 리스트로 만들어서 저장
                for(MultipartFile file : dto.getFiles()){
                    QnaABoardImage image = new QnaABoardImage();

                    String fileName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "QnaA" + File.separator + uuid + "_" + fileName;

                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

                    image.setQnaAUrl(saveName);
                    image.setQnaACode(prev);
                    service.createImg(image);
                }
            } else {
                log.info("기존 사진 x, 추가 사진 o");
                // 추가되는 사진 리스트로 받아서 저장
                for(MultipartFile file : dto.getFiles()){
                    if(file.getOriginalFilename() != ""){
                        QnaABoardImage image = new QnaABoardImage();

                        String fileName = file.getOriginalFilename();
                        String uuid = UUID.randomUUID().toString();
                        String saveName = uploadPath + File.separator + "QnaA" + File.separator + uuid + "_" + fileName;

                        Path savePath = Paths.get(saveName);
                        file.transferTo(savePath);

                        image.setQnaAUrl(saveName);
                        image.setQnaACode(prev);
                        service.createImg(image);
                    }
                }
            }
        } else {
            // 추가되는 사진이 없을 때
            if(prev.getFiles() == null){
                log.info("기존 사진 x, 추가 사진 x");
            } else {
                log.info("기존 사진 o, 추가 사진 x");
                // 추가 사진은 없지만 삭제를 원하지 않을 때


                // 추가 사진 없이 삭제만 원할 때
//                List<QnaABoardImage> list = prev.getFiles();
//                for(QnaABoardImage img : list){
//                    service.deleteImg(img.getQnaABoardImgCode());
//                }
            }
        }
            }
        }
        QnaABoard target = service.update(vo);
        return (target != null) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(target) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 답변 삭제
    @DeleteMapping("/answer/{code}")
    public ResponseEntity<QnaABoard> delete(@PathVariable(name="code") int code){
        QnaABoard prev = service.view(code);

        Object principal = authentication();

        QnaABoard vo = new QnaABoard();

        if(principal instanceof User) {
            User user = (User) principal;

            if (user.getUserRole().equals("ROLE_ADMIN") && prev.getUserId().equals(user.getUserId())) {

                if (prev.getFiles() != null) {
                    QnaABoard target = service.delete(code);

                    QnaQBoard update = questionService.view(target.getQnaQCode());
                    update.setQnaQStatus("N");
                    questionService.update(update);
                }
            }
        }
        return (prev != null) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(prev) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
