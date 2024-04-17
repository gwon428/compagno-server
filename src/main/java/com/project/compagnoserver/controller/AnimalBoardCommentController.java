package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardComment;
import com.project.compagnoserver.domain.Animal.AnimalBoardCommentDTO;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.AnimalBoardCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class AnimalBoardCommentController {

    LocalDateTime localDateTime = LocalDateTime.now();
    Date nowDate = java.sql.Timestamp.valueOf(localDateTime);
    @Autowired
    private AnimalBoardCommentService animalBoardCommentService;

    // 게시글 댓글쓰기
    @PostMapping("/animal-board/comment")
    public ResponseEntity<AnimalBoardComment> writeComment(@RequestBody AnimalBoardCommentDTO dto) {

        log.info("dto : " + dto);
        AnimalBoardComment comment = new AnimalBoardComment();
        comment.setAnimalCommentContent(dto.getAnimalCommentContent());
        comment.setAnimalCommentDate(nowDate);
        AnimalBoard board = new AnimalBoard();
        board.setAnimalBoardCode(dto.getAnimalBoardCode()); // 어떤 글에 쓴 댓글
        User user = new User();
        user.setUserId(dto.getUserId());
        comment.setAnimalBoardCode(board);
//        comment.setUser(user);

        AnimalBoardComment writtenComment = animalBoardCommentService.writeComment(comment);
        log.info("writtenComment : " + writtenComment);

    return writtenComment!=null ?  ResponseEntity.ok(writtenComment) : ResponseEntity.badRequest().build();
    }
}
