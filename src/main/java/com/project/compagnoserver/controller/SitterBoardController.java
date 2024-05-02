package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.SitterBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.SitterBoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class SitterBoardController {

    @Autowired
    private SitterBoardService sitterBoardService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 전체 보기
    @GetMapping("/sitter")
    public ResponseEntity<List<SitterBoard>> sitterViewAll() {
        List<SitterBoard> sitterList = sitterBoardService.sitterViewAll();
        return ResponseEntity.status(HttpStatus.OK).body(sitterList);
    }

    // 상세 보기
    @GetMapping("/sitter/{code}")
    public ResponseEntity<SitterBoard> sitterView(@PathVariable("code") int code) {
        SitterBoard sitterBoard = sitterBoardService.sitterView(code);
        return ResponseEntity.status(HttpStatus.OK).body(sitterBoard);
    }

    // 글 등록
    @PostMapping("/sitter")
    public ResponseEntity<SitterBoard> sitterCreate(SitterBoardDTO sitterBoardDTO) throws IOException {

        Object principal = authentication();
        if(principal == null || !(principal instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        SitterBoard sitter = new SitterBoard();
        SitterCategory sitterCategory = new SitterCategory();
        sitterCategory.setSitterCategoryCode(sitterBoardDTO.getSitterBoardCode());
        sitter.setSitterCategory(sitterCategory);
        sitter.setSitterLocation(sitterBoardDTO.getSitterLocation());
        sitter.setSitterTitle(sitterBoardDTO.getSitterTitle());
        sitter.setSitterContent(sitterBoardDTO.getSitterContent());

        SitterBoard sitterBoard = sitterBoardService.sitterCreate(sitter);

        // 이미지
        for(MultipartFile file : sitterBoardDTO.getFiles()) {
            log.info("file : " + file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + "sitterBoard" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            file.transferTo((savePath));

            SitterBoardImage sitterImg = new SitterBoardImage();
            sitterImg.setSitterImg(saveName);
            sitterImg.setSitterBoard(sitterBoard);
            sitterBoardService.sitterCreateImg(sitterImg);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 글 수정
    @PutMapping("/sitter")
    public ResponseEntity<SitterBoard> sitterUpdate(SitterBoard sitterBoard) {
        sitterBoardService.sitterUpdate(sitterBoard);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 글 삭제
    @Transactional
    @DeleteMapping("/sitter/{code}")
    public ResponseEntity<SitterBoard> sitterDelete(@PathVariable("code") int code) {
        // 이미지 삭제
        List<SitterBoardImage> uploadedImg = sitterBoardService.sitterViewImg(code);
        for(SitterBoardImage image : uploadedImg) {
            sitterBoardService.sitterDeleteImg(image.getSitterImgCode()); // DB에서 삭제
            File file = new File(image.getSitterImg());
            file.delete(); // 파일 삭제
        }
        // 글 삭제
        sitterBoardService.sitterDelete(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    // 조회수
    public void sitterViewCount(int code, HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElseGet(() -> new Cookie[0]);

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("sitterBoardView"))
                .findFirst()
                .orElseGet(() -> {
                    sitterBoardService.sitterViewCount(code);
                    return new Cookie("sitterBoardView", "[" + code + "]");
                });

        if(!cookie.getValue().contains("[" + code + "]")) {
            sitterBoardService.sitterViewCount(code);
            cookie.setValue(cookie.getValue() + "[" + code + "]");
        }
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        res.addCookie(cookie);
    }

    // 댓글 추가
    @PostMapping("/sitter/comment")
    public ResponseEntity sitterCommentCreate(@RequestBody SitterBoardComment sitterBoardComment) {
        Object principal = authentication();

        if(principal instanceof User) {
            User user = (User) principal;
            sitterBoardComment.setUser(user);
            return ResponseEntity.ok(sitterBoardService.sitterCommentCreate(sitterBoardComment));
        }

        return ResponseEntity.badRequest().build();
    }

    // 댓글 수정
    @PutMapping("/sitter/comment")
    public ResponseEntity<SitterBoardComment> sitterCommentUpdate(SitterCommentDTO sitterCommentDTO) {
        log.info("dto : " + sitterCommentDTO);
        SitterBoardComment comment = sitterBoardService.sitterCommentview(sitterCommentDTO.getSitterCommentCode());

        log.info("comment : " + comment);

        comment.setSitterCommentCode(sitterCommentDTO.getSitterCommentCode());
        comment.setSitterCommentContent(sitterCommentDTO.getSitterCommentContent());

        sitterBoardService.sitterCommentUpdate(comment);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/sitter/comment/{commentCode}")
    public ResponseEntity<SitterBoardComment> sitterCommentDelete(@PathVariable("commentCode") int commentCode) {
        sitterBoardService.sitterCommentDelete(commentCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 각 게시판에 대한 댓글 조회
    @GetMapping("/sitter/{code}/comment")
    public ResponseEntity<List<SitterCommentDTO>> sitterViewAllComment(@PathVariable(name = "code") int code) {
        log.info("code : " + code);
        List<SitterBoardComment> topList = sitterBoardService.getTopComments(code);
        List<SitterCommentDTO> response = new ArrayList<>();

        for(SitterBoardComment top : topList) {
            List<SitterBoardComment> replies = sitterBoardService.getReplyComments(top.getSitterCommentCode(), code);

            List<SitterCommentDTO> repliesDTO = new ArrayList<>();

            for(SitterBoardComment reply : replies) {
                SitterCommentDTO dto = SitterCommentDTO.builder()
                        .sitterBoardCode(reply.getSitterBoardCode())
                        .sitterCommentCode(reply.getSitterCommentCode())
                        .sitterCommentContent(reply.getSitterCommentContent())
                        .sitterCommentRegiDate(reply.getSitterCommentRegiDate())
                        .user(UserDTO.builder()
                                .userId(reply.getUser().getUserId())
                                .build())
                        .build();
                repliesDTO.add(dto);
            }

            SitterCommentDTO dto = SitterCommentDTO.builder()
                    .sitterBoardCode(top.getSitterBoardCode())
                    .sitterCommentCode(top.getSitterCommentCode())
                    .sitterCommentContent(top.getSitterCommentContent())
                    .sitterCommentRegiDate(top.getSitterCommentRegiDate())
                    .user(UserDTO.builder()
                            .userId(top.getUser().getUserId())
                            .build())
                    .sitterReplies(repliesDTO)
                    .build();
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }


    public Object authentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication != null ? authentication.getPrincipal() : null;
    }
}
