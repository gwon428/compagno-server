package com.project.compagnoserver.controller;


import com.project.compagnoserver.domain.Animal.*;
import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardDTO;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.AnimalBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class AnimalBoardController {

    @Autowired
    private AnimalBoardService animalBoardService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 시간정보 넣기
    LocalDateTime localDateTime = LocalDateTime.now();
    Date nowDate = java.sql.Timestamp.valueOf(localDateTime);


    public  Object Authentication(){
        // 시큐리티에 담은 로그인한 사용자 정보 가져오기
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getPrincipal();
    }


    // 자유게시판 글쓰기
    @PostMapping("/animal-board")
    public ResponseEntity<AnimalBoard> write(@RequestBody AnimalBoardDTO dto) throws IOException {
        log.info("dto : " + dto);

        Object principal = Authentication();
        if(principal instanceof User) {
            User user = (User) principal;
            // 글작성
            AnimalBoard board = AnimalBoard.builder()
                    .animalBoardTitle(dto.getAnimalBoardTitle())
                    .animalBoardContent(dto.getAnimalBoardContent())
                    .animalBoardDate(nowDate)
                    .animalCategory(AnimalCategory.builder()
                            .animalCategoryCode(dto.getAnimalCategoryCode())
                            .build())
                    .user(User.builder()
                            .userId(user.getUserId())
                            .build())
                    .build();
            AnimalBoard writtenBoard = animalBoardService.write(board);
//            AnimalBoard vo = new AnimalBoard(); // 추가로 필요한것 : userId/
//            vo.setAnimalBoardTitle(dto.getAnimalBoardTitle());
//            vo.setAnimalBoardContent(dto.getAnimalBoardContent());
//            vo.setAnimalBoardDate(nowDate);
////        vo.setAnimalBoardCode(dto.getAnimalCategoryCode());
//            AnimalCategory animalCategory = new AnimalCategory(); // board -> category로 animalCategoryCode 가져오기
//            animalCategory.setAnimalCategoryCode(dto.getAnimalCategoryCode());
//            vo.setAnimalCategory(animalCategory);

            //log.info("vo : " + writtenBoard);

            // 글에 필요한 사진 넣기- 어떤 글의 사진? => animal_board_code 필요
//        for(MultipartFile file : dto.getFiles()){
//            log.info("file :" + file.getOriginalFilename());
//
//            String fileName =  file.getOriginalFilename();
//            String uuid = UUID.randomUUID().toString();
//            String saveName = uploadPath + File.separator + "animalBoard" + File.separator + uuid + "_" + fileName;
//            Path savePath = Paths.get(saveName);
//            file.transferTo(savePath);
//
//            AnimalBoardImage image = new AnimalBoardImage();
//            image.setAnimalBoardImage(saveName);
//            image.setAnimalBoard(writtenBoard);
//            animalBoardService.writeImages(image);

//        }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    // 무한페이징 처리가 필요
    @GetMapping("public/animal-board")
    public ResponseEntity<List<AnimalBoard>> viewAll(){
        List<AnimalBoard> list = animalBoardService.viewAll();

        return list!=null ? ResponseEntity.ok(list) : ResponseEntity.badRequest().build();
    }

    // 자유게시판 - 글 한개보기 = 게시판 상세보기
    @GetMapping("public/animal-board/{animalBoardCode}")
    public ResponseEntity<AnimalBoardDTO> viewDetail(@PathVariable(name = "animalBoardCode")int animalBoardCode){
        animalBoardService.boardView(animalBoardCode);
        AnimalBoard getBoard = animalBoardService.viewDetail(animalBoardCode);
        AnimalBoardDTO getBoardDTO = AnimalBoardDTO.builder()
                .animalBoardDate(getBoard.getAnimalBoardDate())
                .animalBoardTitle(getBoard.getAnimalBoardTitle())
                .animalBoardCode(getBoard.getAnimalBoardCode())
                .animalBoardContent(getBoard.getAnimalBoardContent())
                .animalBoardView(getBoard.getAnimalBoardView())
                .animalMainImage(getBoard.getAnimalMainImage())
                .user(UserDTO.builder()
                        .userId(getBoard.getUser().getUserId())
                        .userNickname(getBoard.getUser().getUserNickname())
                        .userImg(getBoard.getUser().getUserImg())
                        .userRole(getBoard.getUser().getUserRole())
                        .build())
                .animalCategory(getBoard.getAnimalCategory())
                .build();
//    log.info("getdto : " + getBoardDTO);
        return getBoardDTO!=null ? ResponseEntity.ok(getBoardDTO) : ResponseEntity.notFound().build();
    }
    // 자유게시판 - 글 수정
    @PutMapping("/animal-board")
    public ResponseEntity<AnimalBoard> boardUpdate(@RequestBody AnimalBoardDTO dto) throws IOException {
        log.info("dto : " + dto);
//        log.info("dto.code : " + dto.getAnimalBoardCode());

        // 글 수정을 위한 글 객체 생성
        AnimalBoard board = new AnimalBoard();
        board.setAnimalBoardCode(dto.getAnimalBoardCode());
        board.setAnimalBoardTitle(dto.getAnimalBoardTitle());
        board.setAnimalBoardContent(dto.getAnimalBoardContent());
        board.setAnimalMainImage(dto.getAnimalMainImage());
        board.setAnimalBoardDate(dto.getAnimalBoardDate());
//        board.setAnimalCategoryCode(dto.getAnimalCategoryCode());
        AnimalCategory animalCategory = new AnimalCategory();
        animalCategory.setAnimalCategoryCode(dto.getAnimalCategory().getAnimalCategoryCode());
        board.setAnimalCategory(animalCategory);
        AnimalBoard updatedBoard = animalBoardService.boardUpdate(board);

        // 기존 데이터(사진) 가져오기 그리고 바로 삭제
//        AnimalBoard writtenBoard= animalBoardService.viewDetail(dto.getAnimalBoardCode());
//        if(writtenBoard.getImages()!=null){
//            for(AnimalBoardImage image : writtenBoard.getImages()){
//
//                for(MultipartFile file : dto.getFiles()){
//                    if (file!=null && !image.equals(file)){
//                        File filed = new File(String.valueOf(image));
//                        filed.delete();
//                        String fileName =  file.getOriginalFilename();
//                        String uuid = UUID.randomUUID().toString();
//                        String saveName = uploadPath + File.separator + "animalBoard" + File.separator + uuid + "_" + fileName;
//                        Path savePath = Paths.get(saveName);
//                        file.transferTo(savePath);
//
//                        AnimalBoardImage imageResult = new AnimalBoardImage();
//                        imageResult.setAnimalBoardImage(saveName);
//                        imageResult.setAnimalBoard(writtenBoard);
//                        animalBoardService.writeImages(imageResult);
//
////                        board.setImages(imageResult);
//                    }
//                }
//
//            }
//
//
//        }

       return  updatedBoard!=null ? ResponseEntity.ok(board) : ResponseEntity.badRequest().build();
    }
    // 자유게시판 - 글 삭제

    // 조회수
//    @GetMapping("/animal-board/view/{animalBoardCode}")
//    public ResponseEntity boardView(@PathVariable(name = "animalBoardCode") int boardCode){
//        log.info("pathCode : " + boardCode);
//        animalBoardService.boardView(boardCode);
//        return ResponseEntity.ok().build();
//    }
}
