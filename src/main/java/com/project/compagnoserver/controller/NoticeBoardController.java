package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.NoticeBoard.NoticeBoard;
import com.project.compagnoserver.domain.NoticeBoard.NoticeBoardDTO;
import com.project.compagnoserver.domain.NoticeBoard.NoticeBoardImage;
import com.project.compagnoserver.domain.NoticeBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.NoticeBoardCommentService;
import com.project.compagnoserver.service.NoticeBoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/compagno/*")
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 6000, allowCredentials = "true")
public class NoticeBoardController {

    @Autowired
    private NoticeBoardService noticeBoard;

    @Autowired
    private NoticeBoardCommentService comment;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 게시물 등록
    @PostMapping("/noticeBoard")
    public ResponseEntity<NoticeBoard> create(NoticeBoardDTO dto) throws IOException {

        // 게시판 작성
        NoticeBoard vo = NoticeBoard.builder()
                .noticeBoardTitle(dto.getNoticeBoardTitle())
                .noticeBoardContent(dto.getNoticeBoardContent())
                .user(userInfo()).build();
        NoticeBoard result = noticeBoard.createBoard(vo);

        // 이미지 업로드
        if (dto.getFiles() != null) {
            for (MultipartFile file : dto.getFiles()) {
                String fileName = file.getOriginalFilename();
                if (!fileName.isEmpty()) {
                    NoticeBoardImage imgVo = new NoticeBoardImage();
                    String uuid = UUID.randomUUID().toString();
                    String saveFileName = "noticeBoard" + File.separator + uuid + "_" + fileName;
                    String saveName = uploadPath + File.separator + saveFileName;
                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

                    imgVo.setNoticeBoard(result);
                    imgVo.setNoticeImage(saveFileName);
                    noticeBoard.createImage(imgVo);
                }
            }
        }

        return result!=null ?
                ResponseEntity.status(HttpStatus.CREATED).body(result) :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/noticeBoard/{code}")
    public ResponseEntity<NoticeBoard> delete(@PathVariable(name="code") int code) {


        // 나머지 이미지 삭제
        List<NoticeBoardImage> prevImage = noticeBoard.viewImage(code);
        for(NoticeBoardImage image : prevImage) {
            File file = new File(uploadPath + File.separator + image.getNoticeImage());
            file.delete();
        }

        // 게시판 삭제
        noticeBoard.deleteBoard(code);
        return ResponseEntity.ok().build();

    }

    // 게시판 조회
    @GetMapping("/public/noticeBoard/{code}")
    public ResponseEntity<?> view(@PathVariable(name="code") int code,
                                  HttpServletRequest req, HttpServletResponse res) {
        NoticeBoard result = noticeBoard.viewBoard(code);
        viewCountUp(code, req, res);
        return ResponseEntity.ok().body(result);
    }

    // 조회수
    public void viewCountUp(int code, HttpServletRequest req, HttpServletResponse res) {

        Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElseGet(() -> new Cookie[0]);

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("noticeBoard"))
                .findFirst()
                .orElseGet(() -> {
                    noticeBoard.viewCountUp(code);
                    return new Cookie("noticeBoard", "[" + code + "]");
                });

        if(!cookie.getValue().contains("[" + code + "]")) {
            noticeBoard.viewCountUp(code);
            cookie.setValue(cookie.getValue() + "[" + code + "]");
        }
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        res.addCookie(cookie);
    }

    // 게시판 수정
    @PatchMapping("/noticeBoard")
    public ResponseEntity<NoticeBoard> update(NoticeBoardDTO dto) throws IOException {
        NoticeBoard prev = noticeBoard.viewBoard(dto.getNoticeBoardCode());

        // 게시판 수정
        NoticeBoard vo = NoticeBoard.builder()
                .noticeBoardCode(dto.getNoticeBoardCode())
                .noticeBoardTitle(dto.getNoticeBoardTitle())
                .noticeBoardContent(dto.getNoticeBoardContent())
                .noticeBoardRegiDate(prev.getNoticeBoardRegiDate())
                .user(userInfo())
                .build();

        NoticeBoard result = noticeBoard.updateBoard(vo);
        List<NoticeBoardImage> prevImage = noticeBoard.viewImage(dto.getNoticeBoardCode());
        for(NoticeBoardImage image : prevImage) {
            if ((dto.getImages() != null && !dto.getImages().contains(image.getNoticeImage()) || dto.getImages() == null)) {
                File file = new File(uploadPath + File.separator + image.getNoticeImage());
                file.delete();

                noticeBoard.deleteImage(image.getNoticeImageCode());
            }

        }
        // 이미지 업로드
        if (dto.getFiles() != null) {
            for (MultipartFile file : dto.getFiles()) {
                NoticeBoardImage imgVo = new NoticeBoardImage();

                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveFileName = "noticeBoard" + File.separator + uuid + "_" + fileName;
                String saveName = uploadPath + File.separator + saveFileName;

                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                imgVo.setNoticeBoard(result);
                imgVo.setNoticeImage(saveFileName);

                noticeBoard.createImage(imgVo);
            }
        }
        return result!=null ?
                ResponseEntity.status(HttpStatus.CREATED).body(result) :
                ResponseEntity.badRequest().build();
    }

    @PostMapping("/noticeBoard/comment")
    public ResponseEntity<NoticeBoardComment> createComment(@RequestBody NoticeBoardCommentDTO dto) {
        NoticeBoardComment vo = NoticeBoardComment.builder()
                .noticeBoard(NoticeBoard.builder()
                        .noticeBoardCode(dto.getNoticeBoardCode())
                        .build())
                .noticeCommentCode(dto.getNoticeCommentCode())
                .noticeCommentContent(dto.getNoticeCommentContent())
                .noticeParentCode(dto.getNoticeParentCode())
                .user(User.builder()
                        .userId(userInfo().getUserId())
                        .build())
                .build();
        comment.create(vo);
        return ResponseEntity.ok().build();
    }

    // 댓글 수정
    @PatchMapping("/noticeBoard/comment")
    public ResponseEntity updateComment(@RequestBody NoticeBoardCommentDTO dto) {
        NoticeBoardComment vo = NoticeBoardComment.builder()
                .noticeBoard(NoticeBoard.builder()
                        .noticeBoardCode(dto.getNoticeBoardCode())
                        .build())
                .noticeCommentCode(dto.getNoticeCommentCode())
                .noticeCommentContent(dto.getNoticeCommentContent())
                .user(User.builder()
                        .userId(userInfo().getUserId())
                        .build())
                .build();
        comment.update(vo);

        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/noticeBoard/comment/{code}")
    public ResponseEntity deleteComment(@PathVariable (name = "code") int code) {
        comment.delete(code);
        return ResponseEntity.ok().build();
    }


    // 댓글 조회
    @GetMapping("/public/noticeBoard/comment/{code}")
    public ResponseEntity<List<NoticeBoardCommentDTO>> viewComment(@PathVariable (name = "code") int code) {
        List<NoticeBoardComment> list = comment.getTopLevelComments(code);
        List<NoticeBoardCommentDTO> response = new ArrayList<>();
        for(NoticeBoardComment item : list) {
            List<NoticeBoardComment> comments = comment.getBottomComments(item.getNoticeCommentCode() ,code);
            List<NoticeBoardCommentDTO> replies = new ArrayList<>();

            for(NoticeBoardComment comment : comments) {
                NoticeBoardCommentDTO dto = NoticeBoardCommentDTO.builder()
                        .noticeBoardCode(comment.getNoticeBoard().getNoticeBoardCode())
                        .noticeCommentCode(comment.getNoticeCommentCode())
                        .noticeCommentContent(comment.getNoticeCommentContent())
                        .noticeCommentRegiDate(comment.getNoticeCommentRegiDate())
                        .noticeCommentDelete(comment.getNoticeCommentDelete())
                        .user(comment.getUser())
                        .build();
                replies.add(dto);
            }
            NoticeBoardCommentDTO dto = NoticeBoardCommentDTO.builder()
                    .noticeBoardCode(item.getNoticeBoard().getNoticeBoardCode())
                    .noticeCommentCode(item.getNoticeCommentCode())
                    .noticeCommentContent(item.getNoticeCommentContent())
                    .noticeCommentRegiDate(item.getNoticeCommentRegiDate())
                    .noticeCommentDelete(item.getNoticeCommentDelete())
                    .replies(replies)
                    .user(item.getUser())
                    .build();
            response.add(dto);
        }
        return ResponseEntity.ok(response);
    }



    // 로그인 정보
    public User userInfo() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        if(principal instanceof User) {
            User user = (User) principal;
            return user;
        }
        return null;
    }

    @GetMapping("/public/noticeBoard")
    public ResponseEntity<Page<NoticeBoard>> searchBoard(
            @RequestParam(name="keyword", required = false)String keyword,
            @RequestParam(name="page", defaultValue = "1")int page) {
        Pageable pageable = PageRequest.of(page - 1, 12, Sort.by("noticeBoardRegiDate").descending());

        Page<NoticeBoard> list = noticeBoard.searchNoticeBoard(keyword, pageable);
        return ResponseEntity.ok().body(list);
    }
}

