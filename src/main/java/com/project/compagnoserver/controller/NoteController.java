package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Note.Note;
import com.project.compagnoserver.domain.Note.NoteDTO;
import com.project.compagnoserver.domain.Note.NoteFIle;
import com.project.compagnoserver.domain.Note.QNote;
import com.project.compagnoserver.service.NoteService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/compagno/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
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
       note.setNoteRegiDate(Timestamp.valueOf(dto.getNoteRegiDate()));

       Note result = service.create(note);
        if(dto.getFiles()!=null) {
            for (MultipartFile file : dto.getFiles()) {
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
        }
        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 보기 ------------------------------------
    // viewAll(전체보기) - 전체쪽지함
    @GetMapping("/note/viewAll/{nickName}")

    public ResponseEntity<Page<Note>> viewAll(@PathVariable(name="nickName")String nickName, @RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="sender", required = false)String sender, @RequestParam(name="receiver", required = false) String receiver, @RequestParam(name="noteTitle", required = false)String noteTitle, @RequestParam(name="noteRegiDate", required = false) String noteRegiDate){

        Sort sort = Sort.by("noteCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        QNote qNote = QNote.note;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = null;
        if(nickName!=null){
            builder.or(qNote.sender.eq(nickName));
            builder.or(qNote.receiver.eq(nickName));
        }

        if(sender!=null){
            expression = qNote.sender.contains(sender);
            builder.and(expression);
        }

        if(receiver!=null){
            expression = qNote.receiver.contains(receiver);
            builder.and(expression);
        }
        if(noteTitle!=null){
            expression = qNote.noteTitle.contains(noteTitle);
            builder.and(expression);
        }
        if(noteRegiDate!=""){
            String start = noteRegiDate+" 00:00:00";
            String end = noteRegiDate+" 23:59:59";

            expression = qNote.noteRegiDate.between(Timestamp.valueOf(start), Timestamp.valueOf(end));
            builder.and(expression);
        }

        return ResponseEntity.ok(service.viewAll(pageable, builder));

    }

    // viewSendBox(보낸 쪽지함)
    @GetMapping("/note/sendBox/{sender}")
    public ResponseEntity<Page<Note>> viewSendBox(@PathVariable(name="sender")String sender, @RequestParam(name="page", defaultValue = "1")int page,  @RequestParam(name="receiver", required = false) String receiver, @RequestParam(name="noteTitle", required = false)String noteTitle, @RequestParam(name="noteRegiDate", required = false) String noteRegiDate){
        Sort sort = Sort.by("noteCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        QNote qNote = QNote.note;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = null;
        if(sender!=null){
            expression = qNote.sender.eq(sender);
            builder.and(expression);
        }
        if(receiver!=null){
            expression = qNote.receiver.contains(receiver);
            builder.and(expression);
        }
        if(noteTitle!=null){
            expression = qNote.noteTitle.contains(noteTitle);
            builder.and(expression);
        }
        if(noteRegiDate!=""){
            String start = noteRegiDate+" 00:00:00";
            String end = noteRegiDate+" 23:59:59";

            expression = qNote.noteRegiDate.between(Timestamp.valueOf(start), Timestamp.valueOf(end));
            builder.and(expression);
        }
//        Page<Note> list = service.viewSendBox(pageable, builder);
//        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
        return ResponseEntity.ok(service.viewSendBox(pageable, builder));
    }

    // viewReceiveBox(받은 쪽지함)
    @GetMapping("/note/receiveBox/{receiver}")
    public ResponseEntity<Page<Note>> viewReceiveBox(@PathVariable("receiver")String receiver, @RequestParam(name="page", defaultValue = "1")int page, @RequestParam(name="sender", required = false) String sender, @RequestParam(name="noteTitle", required = false)String noteTitle, @RequestParam(name="noteRegiDate", required = false) String noteRegiDate){
        Sort sort = Sort.by("noteCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        QNote qNote = QNote.note;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = null;
        if(receiver!=null){
             expression = qNote.receiver.eq(receiver);
            builder.and(expression);
        }

        if(sender!=null){
            expression = qNote.receiver.contains(receiver);
            builder.and(expression);
        }
        if(noteTitle!=null){
            expression = qNote.noteTitle.contains(noteTitle);
            builder.and(expression);
        }
        if(noteRegiDate!=""){
            String start = noteRegiDate+" 00:00:00";
            String end = noteRegiDate+" 23:59:59";

            expression = qNote.noteRegiDate.between(Timestamp.valueOf(start), Timestamp.valueOf(end));
            builder.and(expression);
        }
//        Page<Note> list = service.viewReceiveBox(pageable, builder);
//        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
        return ResponseEntity.ok(service.viewReceiveBox(pageable, builder));

    }

    // 검색 쪽지함
    @GetMapping("/note/search")
    public ResponseEntity<List<Note>> findBySearch(@RequestParam(name="page", defaultValue = "1")int page, @RequestParam(name="sender", required = false)String sender, @RequestParam(name="receiver", required = false) String receiver, @RequestParam(name="noteTitle", required = false)String noteTitle, @RequestParam(name="noteRegiDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Timestamp noteRegiDate){

        Pageable pageable = PageRequest.of(page-1, 10);

        QNote qNote = QNote.note;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = null;
        if(sender!=null){
            expression = qNote.sender.contains(sender);
            builder.and(expression);
        }

        if(receiver!=null){
            expression = qNote.receiver.contains(receiver);
            builder.and(expression);
        }
        if(noteTitle!=null){
            expression = qNote.noteTitle.contains(noteTitle);
            builder.and(expression);
        }
        if(noteRegiDate!=null){
            expression = qNote.noteRegiDate.eq(noteRegiDate);
            builder.and(expression);
        }
//        log.info("builder : " + builder);
        Page<Note> list = service.findBySearch(pageable, builder);
        return ResponseEntity.status(HttpStatus.OK).body(list.getContent());
    }


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





}
