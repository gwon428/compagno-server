package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Note;
import com.project.compagnoserver.domain.NoteDTO;
import com.project.compagnoserver.domain.NoteFIle;
import com.project.compagnoserver.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/compagno/*")
public class NoteController {

    @Autowired
    private NoteService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 추가 ------------------------------------
    @PostMapping("/note")
    public ResponseEntity<Note> create(NoteDTO dto) throws IOException {
       Note note = new Note();
       note.setNoteTitle(dto.getTitle());
       note.setNoteContent(dto.getContent());
       note.setSender(dto.getSender());
       note.setReceiver(dto.getReceiver());

       Note result = service.create(note);

       for(MultipartFile file : dto.getFiles()){
           NoteFIle files = new NoteFIle();

           String fileName = file.getOriginalFilename();
           String uuid = UUID.randomUUID().toString();

           String saveName = uploadPath + File.separator + "note" + File.separator + uuid + "_" + fileName;
           Path savePath = Paths.get(saveName);
           file.transferTo(savePath);

           files.setNoteFileUrl(saveName);
           // files.setCode
           service.createFile(files);
       }


        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    // 보기 ------------------------------------

    // 삭제 ------------------------------------
}
