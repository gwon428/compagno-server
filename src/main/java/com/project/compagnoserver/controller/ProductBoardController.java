package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.ProductBoard.ProductBoard;
import com.project.compagnoserver.domain.ProductBoard.ProductBoardDTO;
import com.project.compagnoserver.domain.ProductBoard.ProductBoardImage;
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
    private String uploadPath; // D:\\upload

    // 게시물 등록
    @PostMapping("/productBoard")
    public ResponseEntity<ProductBoard> create(ProductBoardDTO dto) throws IOException {

        // 메인 이미지 업로드
        String fileName = dto.getProductMainFile().getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveName = uploadPath + File.separator + "productBoardMainImage" + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName);
        dto.getProductMainFile().transferTo(savePath);

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
            ProductBoardImage imgVo = new ProductBoardImage();
            fileName = file.getOriginalFilename();
            uuid = UUID.randomUUID().toString();
            saveName = uploadPath + File.separator + "productBoardImage" + File.separator + uuid + "_" + fileName;
            savePath = Paths.get(saveName);
            file.transferTo(savePath);

            imgVo.setProductBoard(result);
            imgVo.setProductImage(saveName);
            productBoard.createImage(imgVo);
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
        File file = new File(prev.getProductMainImage());
        file.delete();

        // 나머지 이미지 삭제
        List<ProductBoardImage> prevImage = productBoard.viewImage(code);
        for(ProductBoardImage image : prevImage) {
             file = new File(image.getProductImage());
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
    private void viewCountUp(int code, HttpServletRequest req, HttpServletResponse res) {
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


}
