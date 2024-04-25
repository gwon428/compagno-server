package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AnimalCategory;
import com.project.compagnoserver.domain.NeighborBoard.NeighborBoard;
import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardDTO;
import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardImage;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetFaq;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.NeighborBoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/compagno/*")
@Slf4j
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class NeighborBoardController {

    @Autowired
    private NeighborBoardService neighborBoardService;

//    @Autowired
//    private NeighborBoardCommentService

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 전체 보기
    @GetMapping("public/neighbor")
    public ResponseEntity<List<NeighborBoard>> neighborViewAll() {
        List<NeighborBoard> list = neighborBoardService.neighborViewAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    // 상세 페이지
    @GetMapping("public/neighbor/{code}")
    public ResponseEntity<NeighborBoard> neighborView(@PathVariable(name = "code") int code) {
        NeighborBoard vo = neighborBoardService.neighborView(code);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    // 게시글 등록
    @PostMapping("neighbor")
    public ResponseEntity<NeighborBoard> neighborCreate(NeighborBoardDTO neighborBoardDTO) throws IOException {
        // 게시글 작성
        NeighborBoard neighbor = NeighborBoard.builder()
                .animalCategoryCode(AnimalCategory.builder()
                        .animalCategoryCode(neighborBoardDTO.getAnimalCategoryCode()).build())
                .neighborLocation((neighborBoardDTO.getNeighborLocation()))
                .neighborBoardTitle(neighborBoardDTO.getNeighborBoardTitle())
                .neighborBoardContent(neighborBoardDTO.getNeighborBoardContent())
                .userId(userInfo())
                .build();
        NeighborBoard result = neighborBoardService.neighborCreate(neighbor);

        // 이미지 업로드
        for (MultipartFile file : neighborBoardDTO.getFiles()) {
            String fileName = file.getOriginalFilename();
            if(!fileName.isEmpty()) {
                NeighborBoardImage neighborImgVo = new NeighborBoardImage();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "NeighborBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                neighborImgVo.setNeighborBoard(result);
                neighborImgVo.setNeighborImage(saveName);
                neighborBoardService.neighborCreateImg(neighborImgVo);
            }
        }

        return result!=null ? ResponseEntity.status(HttpStatus.CREATED).body(result) : ResponseEntity.badRequest().build();
    }


    // 게시글 수정
    @PutMapping("neighbor")
    public ResponseEntity neighborUpdate(NeighborBoardDTO neighborBoardDTO) {

        // 기존 이미지 삭제
        List<NeighborBoardImage> uploadedimgs = neighborBoardService.neighborViewAllImg(neighborBoardDTO.getNeighborBoardCode());
        for(NeighborBoardImage image : uploadedimgs) {
            if((neighborBoardDTO.getImages()!=null && !neighborBoardDTO.getImages().contains(image.getNeighborImage())) || neighborBoardDTO.getImages() == null) {
                File file = new File(image.getNeighborImage());
                file.delete();

                neighborBoardService.neighborDeleteImg(image.getNeighborImageCode());
            }
        }

        // 새로운 이미지 등록
        if(neighborBoardDTO.getFiles() != null) {
            for(MultipartFile file : neighborBoardDTO.getFiles()) {
                NeighborBoardImage neighborImgVo = new NeighborBoardImage();

                // 등록
                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "neighborBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);

                neighborImgVo.setNeighborImage(saveName);
                neighborImgVo.setNeighborBoard(NeighborBoard.builder().neighborBoardCode(neighborBoardDTO.getNeighborBoardCode()).build());

                neighborBoardService.neighborCreateImg(neighborImgVo);
            }
        }

        // 게시글 수정
        NeighborBoard neighbor = NeighborBoard.builder()
                .neighborBoardCode(neighborBoardDTO.getNeighborBoardCode())
                .animalCategoryCode(AnimalCategory.builder()
                        .animalCategoryCode(neighborBoardDTO.getAnimalCategoryCode()).build())
                .neighborLocation((neighborBoardDTO.getNeighborLocation()))
                .neighborBoardTitle(neighborBoardDTO.getNeighborBoardTitle())
                .neighborBoardContent(neighborBoardDTO.getNeighborBoardContent())
                .userId(userInfo())
                .build();
        neighborBoardService.neighborCreate(neighbor);

        return ResponseEntity.ok().build();
    }


    // 게시글 삭제
    @DeleteMapping("neighbor/{code}")
    public ResponseEntity<NeighborBoard> neighborDelete(@PathVariable(name = "code") int code) {

        // 이미지 삭제
        List<NeighborBoardImage> uploadedImgs = neighborBoardService.neighborViewAllImg(code);
        for(NeighborBoardImage image : uploadedImgs) {
            File file = new File(image.getNeighborImage());
            file.delete();
            neighborBoardService.neighborDeleteImg(image.getNeighborImageCode());
        }

        // 게시글 삭제
        neighborBoardService.neighborDelete(code);
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

}
