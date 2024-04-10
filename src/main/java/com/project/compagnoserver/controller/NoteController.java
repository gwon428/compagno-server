package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Note;
import com.project.compagnoserver.domain.NoteDTO;
import com.project.compagnoserver.domain.NoteFIle;
//import com.project.compagnoserver.domain.QNote;
import com.project.compagnoserver.domain.QNote;
import com.project.compagnoserver.service.NoteService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
import java.lang.annotation.Repeatable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
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
       note.setNoteTitle(dto.getNoteTitle());
       note.setNoteContent(dto.getNoteContent());
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
           files.setNoteCode(result);
           service.createFile(files);
       }

        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 보기 ------------------------------------
    // viewAll(전체보기) - 전체쪽지함
    @GetMapping("/note")
    public ResponseEntity<List<Note>> viewAll(@RequestParam(name="page", defaultValue = "1") int page){
        Sort sort = Sort.by("noteCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<Note> list = service.viewAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
    }

    // viewSendBox(보낸 쪽지함)
    @GetMapping("/note/sendBox/{sender}")
    public ResponseEntity<List<Note>> viewSendBox(@PathVariable(name="sender")String sender, @RequestParam(name="page", defaultValue = "1")int page){
        Sort sort = Sort.by("noteCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        QNote qNote = QNote.note;
        BooleanBuilder builder = new BooleanBuilder();
        if(sender!=null){
            BooleanExpression expression = qNote.sender.eq(sender);
            builder.and(expression);
        }
        Page<Note> list = service.viewSendBox(pageable, builder);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());

    }

    // viewReceiveBox(받은 쪽지함)
    @GetMapping("/note/receiveBox/{receiver}")
    public ResponseEntity<List<Note>> viewReceiveBox(@PathVariable("receiver")String receiver, @RequestParam(name="page", defaultValue = "1")int page){
        Sort sort = Sort.by("noteCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        QNote qNote = QNote.note;
        BooleanBuilder builder = new BooleanBuilder();
        if(receiver!=null){
            BooleanExpression expression = qNote.receiver.eq(receiver);
            builder.and(expression);
        }
        Page<Note> list = service.viewReceiveBox(pageable, builder);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());

    }

    // 검색 쪽지함



    // view(한개 보기)
    @GetMapping("/note/{noteCode}")
    public ResponseEntity<Note> view(@PathVariable("noteCode")int noteCode){
        Note note = service.view(noteCode);
        return ResponseEntity.status(HttpStatus.OK).body(note);
    }

    // 삭제 ------------------------------------
    // 보낸 이가 삭제 원함(수정+조건삭제)
    @DeleteMapping("/note/sender/{noteCode}")
    public ResponseEntity<Note> deletedSender(@PathVariable(name="noteCode")int noteCode){
        Note note = service.updateDeleteSender(noteCode);

        if(note.getDeletedBySender() && note.getDeletedByReceiver()){
            service.delete(noteCode);
        }

        return (note!=null)?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(note):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 받은 이가 삭제 원함(수정+조건삭제)
    @DeleteMapping("/note/receiver/{noteCode}")
    public ResponseEntity<Note> deletedReceiver(@PathVariable(name="noteCode")int noteCode){
        Note note = service.updateDeleteReceiver(noteCode);

        if(note.getDeletedBySender() && note.getDeletedByReceiver()){
            service.delete(noteCode);
        }
        return (note!=null)?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(note):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    // [검색 기능] - 특정 검색 따른 쪽지 조회


}
