package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.QnaA.QnaABoard;
import com.project.compagnoserver.domain.QnaA.QnaABoardDTO;
import com.project.compagnoserver.domain.QnaA.QnaABoardImage;
import com.project.compagnoserver.service.QnaABoardService;
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
import java.util.UUID;

@RestController
@RequestMapping("/compagno/*")
@Slf4j
public class QnaABoardController {
    @Autowired
    private QnaABoardService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 답변 등록
    @PostMapping("/answer")
    public ResponseEntity<QnaABoard> create (QnaABoardDTO dto) throws IOException {
        QnaABoard vo = new QnaABoard();

        vo.setQnaACode(dto.getQnaACode());
        vo.setQnaQCode(dto.getQnaQCode());
        vo.setUserId(dto.getUserId());
        vo.setQnaATitle(dto.getQnaATitle());
        vo.setQnaAContent(dto.getQnaAContent());

        QnaABoard result = service.create(vo);
        if(dto.getFiles() != null){
            for(MultipartFile file : dto.getFiles()){
                if(file.getOriginalFilename() != "") {
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
        return result != null ? ResponseEntity.status(HttpStatus.CREATED).body(result) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/answer")
    public ResponseEntity<QnaABoard> update(QnaABoardDTO dto){
        QnaABoard prev = service.view(dto.getQnaACode());

        QnaABoard vo = new QnaABoard();
        return null;
    }
}
