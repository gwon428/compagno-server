package com.project.compagnoserver.controller;


import com.project.compagnoserver.domain.Animal.*;
import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardDTO;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.AnimalBoardFavoriteService;
import com.project.compagnoserver.service.AnimalBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class AnimalBoardController {

    @Autowired
    private AnimalBoardService animalBoardService;

    @Autowired
    private AnimalBoardFavoriteService favoriteService;

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
    // 이미지 저장 - boardCode == null 인 상태로 시작
    @PostMapping("public/animal-board/image")
    public ResponseEntity<AnimalBoardImage> saveImages(AnimalBoardDTO dto) throws IOException {
        String fileName = dto.getFile().getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveName = uploadPath + File.separator + "animalBoard" + File.separator + uuid + "_" + fileName;

        Path savePath = Paths.get(saveName); // image 저장 경로
        dto.getFile().transferTo(savePath);

        AnimalBoardImage image = AnimalBoardImage.builder()
                .animalBoardImage(saveName.substring(24)) // uploadPath 재확인
                .build(); //    \\DESKTOP-U0CNG13\ upload\여기까지가 25글자   animalBoard전에 딱 끊김

        AnimalBoardImage response = animalBoardService.saveImages(image);
        log.info("controller response : " + response);
        return ResponseEntity.ok(response); // 제일 중요한거! 사진 미리보기등 특정 부분의 결과값 확인을 위해서
    }
    // 자유 게시판 글쓰기
    @PostMapping("/animal-board")
    public ResponseEntity<AnimalBoard> writeBoard(@RequestBody AnimalBoardDTO dto){
        log.info("write dto : " + dto);
        Object principal = Authentication();
        if(principal instanceof  User) {
            User user = (User) principal;

            // 이제 animal_board 에 데이터를 추가함으로써 board_code가 생성됨
            AnimalBoard response = animalBoardService.write(AnimalBoard.builder()
                    .animalBoardContent(dto.getAnimalBoardContent())
                    .animalBoardTitle(dto.getAnimalBoardTitle())
                    .animalBoardDate(nowDate)
                    .animalCategory(AnimalCategory.builder()
                            .animalCategoryCode(dto.getAnimalCategoryCode())
                            .build())
                    .user(User.builder()
                            .userId(user.getUserId()) // 오류나면 dto 로 바꾸기
                            .build())
                    .build());
            /* =================================================================================== */
            // 정규표현식 패턴 설정
            String regex = "<[^>]+>";

            // 패턴 매칭을 위한 Pattern 객체
            Pattern pattern = Pattern.compile(regex);

            // 패턴과 일치하는 부분 찾기 위한 Matcher 객체
            Matcher matcher = pattern.matcher(dto.getAnimalBoardContent());

            List images = new ArrayList(); // 글쓰기에서 최종적으로 남은 이미지 리스트 담을 공간
            // 매칭된 문자열 추출
            while(matcher.find()) {
                if(matcher.group().startsWith("<img")) {
                    String image = matcher.group().substring(35, matcher.group().length() - 2);
                    images.add(image); // 매칭된 이미지들 images에 추가
                    // matcher 가 정규표현식이 매칭되는 이미지태그의 이미지를 iamges 리스트에 담아줌
                    // 얘가 DB에 저장되어야할 아이
                }
            }
            /* =================================================================================== */
            // animal_board_code 가 null 인 이미지 가져오기
            List<AnimalBoardImage> list = animalBoardService.viewImages();
            // 일단 그냥 막 들어와진 이미지들

            for(AnimalBoardImage image : list){
                if(images.contains(image.getAnimalBoardImage())){
                    // 확실이 들어가야하는 이미지리스트와 막무가내 리스트 비교, 두 비교값이 같다면?
                    // 즉, DB에 저장되어야 하는 값을 발견했을때는 true
                    // 해당 이미지들에게 animal_board_code 추가
                    animalBoardService.saveImages(AnimalBoardImage.builder()
                            .animalImageCode(image.getAnimalImageCode())
                            .animalBoardImage(image.getAnimalBoardImage()) // url
                            .animalBoard(AnimalBoard.builder()
                                    .animalBoardCode(response.getAnimalBoardCode())
                                    .build())
                            .build()); // 미리 선언된 response의 boardCode를 여기에 넣어줌
                }else{
                    // 실제 파일 삭제 --> animal_board_code 가 null인 이미지의 실제 사진 삭제
                    File file = new File(uploadPath + image.getAnimalBoardImage());
                    file.delete();
                }
            }

            // 마지막으로 DB상에서 animal_board_Code가 null인 image, image 테이블에서 삭제
            animalBoardService.deleteAnimalNoImages();

            // 완성된 이미지 리스트 불러오기 - 추후에 리스트로 변경하여 사용자에게 선택권한을 줌
            AnimalBoardImage thumnail = animalBoardService.getThumnailList(response.getAnimalBoardCode());
            log.info("thumnail : " + thumnail);
            animalBoardService.saveThumnail(thumnail.getAnimalBoardImage(), response);

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
                .animalBoardFavoriteCount(getBoard.getAnimalBoardFavoriteCount())
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

        Object principal = Authentication();
        if(principal instanceof  User) {
            User user = (User) principal;
            // 그전에 기존 사진 없애기

            // 이번에는 animal_board_code 가 이미 존재함
            AnimalBoard response = animalBoardService.boardUpdate(AnimalBoard.builder()
                    .animalBoardCode(dto.getAnimalBoardCode())
                    .animalBoardContent(dto.getAnimalBoardContent())
                    .animalBoardTitle(dto.getAnimalBoardTitle())
                    .animalBoardDate(dto.getAnimalBoardDate())
                    .animalBoardView(dto.getAnimalBoardView())
//                    .animalMainImage(dto.getAnimalMainImage()) // 썸네일 잊지말기
                    .animalBoardDate(nowDate)
                    .animalCategory(AnimalCategory.builder()
                            .animalCategoryCode(dto.getAnimalCategory().getAnimalCategoryCode())
                            .build())
                    .user(User.builder()
                            .userId(user.getUserId()) // 오류나면 dto 로 바꾸기
                            .build())
                    .build());
            /* =================================================================================== */
            // 정규표현식 패턴 설정
            String regex = "<[^>]+>";

            // 패턴 매칭을 위한 Pattern 객체
            Pattern pattern = Pattern.compile(regex);

            // 패턴과 일치하는 부분 찾기 위한 Matcher 객체
            Matcher matcher = pattern.matcher(dto.getAnimalBoardContent());

            List images = new ArrayList(); // 글쓰기에서 최종적으로 남은 이미지 리스트 담을 공간
            // 매칭된 문자열 추출
            while(matcher.find()) {
                if(matcher.group().startsWith("<img")) {
                    String image = matcher.group().substring(35, matcher.group().length() - 2);
                    images.add(image); // 매칭된 이미지들 images에 추가
                    // matcher 가 정규표현식이 매칭되는 이미지태그의 이미지를 iamges 리스트에 담아줌
                    // 얘가 DB에 저장되어야할 아이
                }
            }
            /* =================================================================================== */
            // animal_board_code 가 null 인 이미지 가져오기
            List<AnimalBoardImage> list = animalBoardService.viewImages();
            // 일단 그냥 막 들어와진 이미지들

            for(AnimalBoardImage image : list){
                if(images.contains(image.getAnimalBoardImage())){
                    // 확실이 들어가야하는 이미지리스트와 막무가내 리스트 비교, 두 비교값이 같다면?
                    // 즉, DB에 저장되어야 하는 값을 발견했을때는 true
                    // 해당 이미지들에게 animal_board_code 추가
                    animalBoardService.saveImages(AnimalBoardImage.builder()
                            .animalImageCode(image.getAnimalImageCode())
                            .animalBoardImage(image.getAnimalBoardImage()) // url
                            .build());
                }else{
                    // 실제 파일 삭제 --> animal_board_code 가 null인 이미지의 실제 사진 삭제
                    File file = new File(uploadPath + image.getAnimalBoardImage());
                    file.delete();
                }
            }

            // 마지막으로 DB상에서 animal_board_Code가 null인 image, image 테이블에서 삭제
            animalBoardService.deleteAnimalNoImages();

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    // 자유게시판 - 글 삭제

    // 조회수
//    @GetMapping("/animal-board/view/{animalBoardCode}")
//    public ResponseEntity boardView(@PathVariable(name = "animalBoardCode") int boardCode){
//        log.info("pathCode : " + boardCode);
//        animalBoardService.boardView(boardCode);
//        return ResponseEntity.ok().build();
//    }

    // 좋아요 여부 확인 정보 가져오기
    @PostMapping("/public/animal-board/checkFavorite")
    public ResponseEntity<Boolean> checkFavorite(@RequestBody AnimalBoardFavoriteDTO dto){
//            log.info("user왔나 : " + dto.getUserId());
//            log.info("여기까지 왔나? : " + dto.getAnimalBoardCode());
        Boolean b = favoriteService.checkFavorite(dto.getAnimalBoardCode(), dto.getUserId());

        return ResponseEntity.ok(b);
    }
    // 좋아요 추가
    @PostMapping("/animal-board/addFavorite")
    public ResponseEntity<AnimalBoardFavorite> favoriteBoard(@RequestBody AnimalBoardFavoriteDTO dto){
        // 필요값 : userId, animalBoardCode
        AnimalBoardFavorite favoriteBoard = AnimalBoardFavorite.builder()
                .animalBoard(AnimalBoard.builder()
                        .animalBoardCode(dto.getAnimalBoardCode())
                        .build())
                .user(User.builder()
                        .userId(dto.getUserId())
                        .build())
                .animalFavoriteDate(nowDate)
                .build();
        favoriteService.favoriteBoard(favoriteBoard);

        return ResponseEntity.ok().build();
    }

    // 좋아요 삭제
    @PostMapping("/animal-board/delFavorite")
    public ResponseEntity<?> deleteFavorite(@RequestBody AnimalBoardFavoriteDTO dto){
        favoriteService.deleteFavorite(dto);
        return ResponseEntity.ok().build();
    }

    // 글마다 좋아요 수 조회
    @PutMapping("/public/animal-board/countFavorite")
    public ResponseEntity<?> favCount(@RequestBody AnimalBoardFavoriteDTO dto){
        log.info("좋아요 dto : " + dto);
        favoriteService.favCount(dto);
        return ResponseEntity.ok().build();
    }
}
