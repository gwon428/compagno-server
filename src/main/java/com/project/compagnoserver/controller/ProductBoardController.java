package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AnimalCategory;
import com.project.compagnoserver.domain.ProductBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.ProductBoardCommentService;
import com.project.compagnoserver.service.ProductBoardService;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@CrossOrigin (origins = {"http://localhost:3000"}, maxAge = 6000, allowCredentials = "true")
public class ProductBoardController {

    @Autowired
    private ProductBoardService productBoard;

    @Autowired
    private ProductBoardCommentService comment;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath; // C:\\upload

    // 게시물 등록
    @PostMapping("/productBoard")
    public ResponseEntity<ProductBoard> create(ProductBoardDTO dto) throws IOException {
        String saveName = null;
        // 메인 이미지 업로드
        if(!dto.getProductMainFile().isEmpty()) {
            String fileName = dto.getProductMainFile().getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            saveName = uploadPath + File.separator + "productBoard" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            dto.getProductMainFile().transferTo(savePath);
        }

        // 게시판 작성
        ProductBoard vo = ProductBoard.builder()
                .productBoardTitle(dto.getProductBoardTitle())
                .productMainImage(saveName)
                .productName(dto.getProductName())
                .productPrice(dto.getProductPrice())
                .productCategory(dto.getProductCategory())
                .productBoardGrade(dto.getProductBoardGrade())
                .productBoardContent(dto.getProductBoardContent())
                .user(userInfo())
                .animalCategory(AnimalCategory.builder()
                        .animalCategoryCode(dto.getAnimalCategoryCode()).build())
                .build();
        ProductBoard result = productBoard.createBoard(vo);

        // 이미지 업로드
        for (MultipartFile file : dto.getFiles()) {
            String fileName = file.getOriginalFilename();
            if(!fileName.isEmpty()) {
                ProductBoardImage imgVo = new ProductBoardImage();
                String uuid = UUID.randomUUID().toString();
                saveName = uploadPath + File.separator + "productBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                imgVo.setProductBoard(result);
                imgVo.setProductImage(saveName);
                productBoard.createImage(imgVo);
            }
        }

        return result!=null ?
                ResponseEntity.status(HttpStatus.CREATED).body(result) :
                ResponseEntity.badRequest().build();
    }


    // 게시판 삭제
    @DeleteMapping("/productBoard/{code}")
    public ResponseEntity<ProductBoard> delete(@PathVariable(name="code") int code) {

        // 메인 이미지 삭제
        ProductBoard prev = productBoard.viewBoard(code);
        if(prev.getProductMainImage()!=null) {
            File file = new File(prev.getProductMainImage());
            file.delete();
        }

        // 나머지 이미지 삭제
        List<ProductBoardImage> prevImage = productBoard.viewImage(code);
        for(ProductBoardImage image : prevImage) {
            File file = new File(image.getProductImage());
            file.delete();
        }

        // 게시판 삭제
        productBoard.deleteBoard(code);
        return ResponseEntity.ok().build();

    }


    // 게시판 조회
    @GetMapping("/public/productBoard/{code}")
    public ResponseEntity<?> view(@PathVariable(name="code") int code,
                                  HttpServletRequest req, HttpServletResponse res) {
        ProductBoard result = productBoard.viewBoard(code);
        viewCountUp(code, req, res);
        return ResponseEntity.ok().body(result);
    }

    // 조회수
    public void viewCountUp(int code, HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElseGet(() -> new Cookie[0]);

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("productBoard"))
                .findFirst()
                .orElseGet(() -> {
                    productBoard.viewCountUp(code);
                    return new Cookie("productBoard", "[" + code + "]");
                });

        if(!cookie.getValue().contains("[" + code + "]")) {
            productBoard.viewCountUp(code);
            cookie.setValue(cookie.getValue() + "[" + code + "]");
        }
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        res.addCookie(cookie);
    }

    // 게시판 수정 (이미지 관련 수정 필요)
    @PutMapping("/productBoard")
    public ResponseEntity<ProductBoard> update(ProductBoardDTO dto) throws IOException {


        // 게시판 수정
        ProductBoard vo = ProductBoard.builder()
                .productBoardCode(dto.getProductBoardCode())
                .productBoardTitle(dto.getProductBoardTitle())
                .productName(dto.getProductName())
                .productPrice(dto.getProductPrice())
                .productCategory(dto.getProductCategory())
                .productBoardGrade(dto.getProductBoardGrade())
                .productBoardContent(dto.getProductBoardContent())
                .user(userInfo())
                .animalCategory(AnimalCategory.builder()
                        .animalCategoryCode(dto.getAnimalCategoryCode()).build())
                .build();

        // 기존 메인 이미지 삭제
        ProductBoard prev = productBoard.viewBoard(dto.getProductBoardCode());
        if(prev.getProductMainImage() != null) {
            if (!prev.getProductMainImage().equals(dto.getMainImage()) || !dto.getProductMainFile().isEmpty()) {
                File file = new File(prev.getProductMainImage());
                file.delete();
            }
        }

        // 메인 이미지 업로드
        if(!dto.getProductMainFile().isEmpty()) {
            String fileName = dto.getProductMainFile().getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + "productBoard" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            dto.getProductMainFile().transferTo(savePath);
            vo.setProductMainImage(saveName);
        }

        if(prev.getProductMainImage() != null && dto.getProductMainFile().isEmpty()
                && prev.getProductMainImage().equals(dto.getMainImage())){
            String saveName = prev.getProductMainImage();
            vo.setProductMainImage(saveName);
        }

        ProductBoard result = productBoard.updateBoard(vo);


        // 나머지 이미지 삭제
        List<ProductBoardImage> prevImage = productBoard.viewImage(dto.getProductBoardCode());
        for(ProductBoardImage image : prevImage) {
            if ((dto.getImages()!=null && !dto.getImages().contains(image.getProductImage())) || dto.getImages() == null) {
                File file = new File(image.getProductImage());
                file.delete();

                productBoard.deleteImage(dto.getProductBoardCode());
            }

        }
        // 이미지 업로드
        if (dto.getFiles() != null) {
            for (MultipartFile file : dto.getFiles()) {
                ProductBoardImage imgVo = new ProductBoardImage();

                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "productBoard" + File.separator + uuid + "_" + fileName;

                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                imgVo.setProductBoard(result);
                imgVo.setProductImage(saveName);

                productBoard.createImage(imgVo);
            }
        }
        return result!=null ?
                ResponseEntity.status(HttpStatus.CREATED).body(result) :
                ResponseEntity.badRequest().build();
    }

    // 게시판 추천, 추천 된 상태면 삭제
    @PostMapping("/productBoard/recommend")
    public ResponseEntity boardRecommend(@RequestBody ProductBoardRecommendDTO dto) {
        log.info("dto : " + dto);
        ProductBoardRecommend vo = new ProductBoardRecommend();
        vo.setUser(User.builder().userId(dto.getUserId()).build());
        vo.setProductBoard(ProductBoard.builder().productBoardCode(dto.getProductBoardCode()).build());

        productBoard.boardRecommend(vo);

        return ResponseEntity.ok().build();
    }

    // 게시판 북마크, 북마크 된 상태면 삭제
    @PostMapping("/productBoard/bookmark")
    public ResponseEntity boardBookmark(@RequestBody ProductBoardBookmarkDTO dto) {
        ProductBoardBookmark vo = new ProductBoardBookmark();
        vo.setUser(User.builder().userId(dto.getUserId()).build());
        vo.setProductBoard(ProductBoard.builder().productBoardCode(dto.getProductBoardCode()).build());
        log.info("vo : " + vo);
        productBoard.boardBookmark(vo);

        return ResponseEntity.ok().build();
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

    // 게시판 검색, 조회
    @GetMapping("/public/productBoard/search")
    public ResponseEntity<Page<ProductBoard>> searchBoard(ProductBoardSearchDTO dto, @RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 12);
        QProductBoard qProductBoard = QProductBoard.productBoard;

        Page<ProductBoard> list = productBoard.searchProductBoard(dto, pageable);
        return ResponseEntity.ok().body(list);
    }
    // 댓글 작성
    @PostMapping("/productBoard/comment")
    public ResponseEntity<ProductBoardComment> createComment(@RequestBody ProductBoardComment vo) {
        vo.setUser(userInfo());
        return ResponseEntity.ok(comment.create(vo));
    }

    // 댓글 수정
    @PatchMapping("/productBoard/comment")
    public ResponseEntity updateComment(@RequestBody ProductBoardComment vo) {
        vo.setUser(userInfo());
        comment.update(vo);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/productBoard/comment/{code}")
    public ResponseEntity deleteComment(@PathVariable (name = "code") int code) {
        comment.delete(code);
        return ResponseEntity.ok().build();
    }

    // 댓글 조회
    @GetMapping("/public/productBoard/comment/{code}")
    public ResponseEntity<List<ProductBoardCommentDTO>> viewComment(@PathVariable (name = "code") int code) {
        List<ProductBoardComment> list = comment.getTopLevelComments(code);
        List<ProductBoardCommentDTO> response = new ArrayList<>();

        for(ProductBoardComment item : list) {
            List<ProductBoardComment> comments = comment.getBottomComments(item.getProductCommentCode() ,code);
            List<ProductBoardCommentDTO> replies = new ArrayList<>();

            for(ProductBoardComment comment : comments) {
                ProductBoardCommentDTO dto = ProductBoardCommentDTO.builder()
                        .productBoardCode(comment.getProductBoard().getProductBoardCode())
                        .productCommentCode(comment.getProductCommentCode())
                        .productCommentContent(comment.getProductCommentContent())
                        .productCommentRegiDate(comment.getProductCommentRegiDate())
                        .user(userInfo())
                        .build();
                replies.add(dto);
            }
            ProductBoardCommentDTO dto = ProductBoardCommentDTO.builder()
                    .productBoardCode(item.getProductBoard().getProductBoardCode())
                    .productCommentCode(item.getProductCommentCode())
                    .productCommentContent(item.getProductCommentContent())
                    .productCommentRegiDate(item.getProductCommentRegiDate())
                    .replies(replies)
                    .user(userInfo())
                    .build();
            response.add(dto);
        }
        return ResponseEntity.ok(response);
    }
}