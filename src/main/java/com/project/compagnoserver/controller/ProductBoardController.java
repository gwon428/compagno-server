package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.ProductBoard.*;
import com.project.compagnoserver.service.ProductBoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/compagno/*")
@Slf4j
public class ProductBoardController {

    @Autowired
    private ProductBoardService productBoard;

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
            saveName = uploadPath + File.separator + "productBoardMainImage" + File.separator + uuid + "_" + fileName;
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
                .userId(dto.getUserId())
                .animalCategoryCode(dto.getAnimalCategoryCode())
                .build();
        ProductBoard result = productBoard.createBoard(vo);

        // 이미지 업로드
        for (MultipartFile file : dto.getFiles()) {
            String fileName = file.getOriginalFilename();
            if(!fileName.isEmpty()) {
                ProductBoardImage imgVo = new ProductBoardImage();
                String uuid = UUID.randomUUID().toString();
                saveName = uploadPath + File.separator + "productBoardImage" + File.separator + uuid + "_" + fileName;
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
    @GetMapping("/productBoard/{code}")
    public ResponseEntity<ProductBoard> view(@PathVariable(name="code") int code,
                                             HttpServletRequest req, HttpServletResponse res) {
        ProductBoard result = productBoard.viewBoard(code);
        viewCountUp(code, req, res);
        return ResponseEntity.ok().body(result);
    }

    // 조회수
    public void viewCountUp(int code, HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElseGet(() -> new Cookie[0]);

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("productBoardView"))
                .findFirst()
                .orElseGet(() -> {
                    productBoard.viewCountUp(code);
                    return new Cookie("productBoardView", "[" + code + "]");
                });

        if(!cookie.getValue().contains("[" + code + "]")) {
            productBoard.viewCountUp(code);
            cookie.setValue(cookie.getValue() + "[" + code + "]");
        }
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        res.addCookie(cookie);
    }

    // 게시판 수정
    @PutMapping("/productBoard")
    public ResponseEntity<ProductBoard> update(ProductBoardDTO dto) throws IOException {

        // 기존 이미지 삭제
        ProductBoard prev = productBoard.viewBoard(dto.getProductBoardCode());
        if(prev.getProductMainImage()!=null) {
            File file = new File(prev.getProductMainImage());
            file.delete();
        }

        // 나머지 이미지 삭제
        List<ProductBoardImage> prevImage = productBoard.viewImage(dto.getProductBoardCode());
        for(ProductBoardImage image : prevImage) {
            File file = new File(image.getProductImage());
            file.delete();
            productBoard.deleteImage(dto.getProductBoardCode());
        }

        // 메인 이미지 업로드
        String saveName = null;
        if(!dto.getProductMainFile().isEmpty()) {
            String fileName = dto.getProductMainFile().getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            saveName = uploadPath + File.separator + "productBoardMainImage" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            dto.getProductMainFile().transferTo(savePath);
        }

        // 게시판 작성
        ProductBoard vo = ProductBoard.builder()
                .productBoardCode(dto.getProductBoardCode())
                .productBoardTitle(dto.getProductBoardTitle())
                .productMainImage(saveName)
                .productName(dto.getProductName())
                .productPrice(dto.getProductPrice())
                .productCategory(dto.getProductCategory())
                .productBoardGrade(dto.getProductBoardGrade())
                .productBoardContent(dto.getProductBoardContent())
                .userId(dto.getUserId())
                .animalCategoryCode(dto.getAnimalCategoryCode())
                .build();
        ProductBoard result = productBoard.updateBoard(vo);

        // 이미지 업로드
        for (MultipartFile file : dto.getFiles()) {
            String fileName = file.getOriginalFilename();
            if(!fileName.isEmpty()) {
                ProductBoardImage imgVo = new ProductBoardImage();
                String uuid = UUID.randomUUID().toString();
                saveName = uploadPath + File.separator + "productBoardImage" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                imgVo.setProductBoard(result);
                imgVo.setProductImage(saveName);
            }
        }

        return result!=null ?
                ResponseEntity.status(HttpStatus.CREATED).body(result) :
                ResponseEntity.badRequest().build();
    }

    // 게시판 추천, 추천 된 상태면 삭제
    @PostMapping("/productBoard/recommend")
    public ResponseEntity boardRecommend(@RequestBody ProductBoardRecommend vo) {
        vo.setUserId("ghldud"); // 나중에 user 정보
        productBoard.boardRecommend(vo);

        return ResponseEntity.ok().build();
    }

    // 게시판 북마크, 북마크 된 상태면 삭제
    @PostMapping("/productBoard/bookmark")
    public ResponseEntity boardBookmark(@RequestBody ProductBoardBookmark vo) {
        vo.setUserId("ghldud"); // 나중에 user 정보
        productBoard.boardBookmark(vo);

        return ResponseEntity.ok().build();
    }
}