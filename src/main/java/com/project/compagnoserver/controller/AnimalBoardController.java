package com.project.compagnoserver.controller;


import com.project.compagnoserver.domain.Animal.*;
import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardDTO;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.AnimalBoardCommentService;
import com.project.compagnoserver.service.AnimalBoardFavoriteService;
import com.project.compagnoserver.service.AnimalBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.*;
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

    @Autowired
    private AnimalBoardCommentService animalBoardCommentService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 시간정보 넣기

    public Date currentDate (){
        LocalDateTime localDateTime = LocalDateTime.now();
        return  java.sql.Timestamp.valueOf(localDateTime);
    }

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
    @PostMapping("/animal-board")
    public ResponseEntity<AnimalBoard> writeBoard(@RequestBody AnimalBoardDTO dto){
        Date nowDate = currentDate();

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
                    String image = matcher.group().substring(35, matcher.group().length() - 2); //image-resize에 안 들어간 경우
                    if(image.contains("\"")){
                        String[] finalizedArr = image.split("\"");
                        String finalizedImage = finalizedArr[0];
//                        log.info("db에 저장될 이미지 : " + image);
//                        log.info("2차 처리 이미지 배열: " + finalizedArr[0]);
//                        log.info("2차 처리 이미지 최종본: " + finalizedImage);
                        images.add(finalizedImage);
                        // image-resize가 적용될 경우 split을 통해서 이미지 문자열만 2차 재추출 해야함.
                    }else{
                        images.add(image); // 매칭된 이미지들 images에 추가
                        // matcher 가 정규표현식이 매칭되는 이미지태그의 이미지를 iamges 리스트에 담아줌
                        // 얘가 DB에 저장되어야할 아이
                    }


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
            AnimalBoardImage thumbnail = animalBoardService.getThumbnailList(response.getAnimalBoardCode());
            log.info("thumbnail : " + thumbnail);
            if(thumbnail!=null){
                animalBoardService.saveThumbnail(thumbnail.getAnimalBoardImage(), response);
            } else{
                animalBoardService.saveThumbnail("animalDefault.jpg", response);
            }


            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    // 무한페이징 처리가 필요 + 조회
    @GetMapping("public/animal-board")
    public ResponseEntity<Page<AnimalBoard>> viewAll(@RequestParam(name = "page", defaultValue = "1")int page,
                                                     @RequestParam(name = "animalCategory", required = false)Integer animalCategory,
                                                     @RequestParam(name = "sortBy" , defaultValue = "0")Integer sortBy){
    log.info("page : " + page);
        log.info("animalCategoryCode : " + animalCategory);
        log.info("sortBy : " + sortBy);
        // category 검색
        QAnimalBoard qAnimalBoard = QAnimalBoard.animalBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;

        if(animalCategory!=null){
            expression = qAnimalBoard.animalCategory.animalCategoryCode.eq(animalCategory);
            builder.and(expression);
        }


        // 정렬
        Sort sort = null;
        switch (sortBy){
            case 1: // 조회수
                sort = Sort.by("animalBoardView").descending();
                break;
            case 2: // 좋아요
                sort = Sort.by("animalBoardFavoriteCount").descending();
                break;
            case 3: // 옛날순
                sort = Sort.by("animalBoardCode").ascending();
                break;
            default: // 최신순
                sort = Sort.by("animalBoardDate").descending();
                break;
        }
        // 조회수, 좋아요, 전체
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        Page<AnimalBoard> list = animalBoardService.viewAll(pageable, builder);
        log.info("최종 list : " + list);

        return list!=null ? ResponseEntity.ok(list) : ResponseEntity.badRequest().build();
    }
    @GetMapping("public/animal-board/weeklyRank-fav") // with boardCode eq favoriteBoard
    public ResponseEntity<List<AnimalBoardFavoriteDTO>> favList (){
        List<AnimalBoardFavorite> list = animalBoardService.favList();
        List<AnimalBoardFavoriteDTO> listDTO = new ArrayList<>();
        for(AnimalBoardFavorite  fav : list){
            AnimalBoardFavoriteDTO dto =AnimalBoardFavoriteDTO.builder()
                    .animalFavoriteCode(fav.getAnimalFavoriteCode())
                    .animalBoardCode(fav.getAnimalBoard().getAnimalBoardCode())
                    .animalFavoriteDate(fav.getAnimalBoard().getAnimalBoardDate())
                    .userId(fav.getUser().getUserId())
                    .build();
            listDTO.add(dto);
        }
        log.info("favDTO : " + listDTO);
        return ResponseEntity.ok(listDTO);
    }

    //랭킹 - 좋아요 상위권 불러오기
    @GetMapping("public/animal-board/weeklyRank")
    public ResponseEntity<List<AnimalBoardDTO>> viewRankers (){
        List<AnimalBoard> list = animalBoardService.viewRankers(); //toplist
        List<AnimalBoardDTO> listDTO = new ArrayList<>();
        for(AnimalBoard part :list){
//            List<AnimalBoardFavorite> favList = animalBoardService.favList(part.getAnimalBoardCode());
//            List<AnimalBoardFavoriteDTO> favListDTO = new ArrayList<>();
//            for(AnimalBoardFavorite child : favList){
//                AnimalBoardFavoriteDTO dto =AnimalBoardFavoriteDTO.builder()
//                        .animalBoardCode(child.getAnimalBoard().getAnimalBoardCode())
//                        .animalFavoriteDate(child.getAnimalFavoriteDate())
//                        .build();
//                favListDTO.add(dto);
//            }
            AnimalBoardDTO dto = AnimalBoardDTO.builder()
                    .animalBoardCode(part.getAnimalBoardCode())
                    .animalBoardView(part.getAnimalBoardView())
                    .animalBoardTitle(part.getAnimalBoardTitle())
                    .animalBoardFavoriteCount(part.getAnimalBoardFavoriteCount())
                    .user(UserDTO.builder()
                            .userId(part.getUser().getUserId())
                            .userImg(part.getUser().getUserImg())
                            .userNickname(part.getUser().getUserNickname())
                            .build())
                    .build();
            listDTO.add(dto);

        }

        return  ResponseEntity.ok(listDTO);
    }
    // 카테고리 불러오기
    @GetMapping("public/animal-board/animalCategory")
    public ResponseEntity<List<AnimalCategory>> viewCategory(){
        List<AnimalCategory> categoryList = animalBoardService.viewCategory();
        return ResponseEntity.ok(categoryList);
    }

    // 자유게시판 - 글 한개보기 = 조회수
    @GetMapping("public/animal-board/{animalBoardCode}/viewCount")
    public ResponseEntity<Integer> viewCount(@PathVariable(name = "animalBoardCode") int animalBoardCode){
        log.info("조회수 : " + animalBoardCode);
        animalBoardService.boardView(animalBoardCode);
        return ResponseEntity.ok().build();
    }
    // 자유게시판 - 글 한개보기 = 게시판 상세보기
    @GetMapping("public/animal-board/{animalBoardCode}")
    public ResponseEntity<AnimalBoardDTO> viewDetail(@PathVariable(name = "animalBoardCode")int animalBoardCode){

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
    // 자유게시판 수정 : 기존 이미지 가져오기
    @GetMapping("public/animal-board/{animalBoardCode}/prevImages")
    public ResponseEntity<List<AnimalBoardImage>> getPrevImages(@PathVariable(name = "animalBoardCode")int boardCode){
        log.info("글 수정 boardCOde: " + boardCode);
        List<AnimalBoardImage> prevImages = animalBoardService.getPrevImages(boardCode);
        log.info("prevImages : " + prevImages);
        return prevImages!=null ?  ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    // 자유게시판 - 글 수정
    @PutMapping("/animal-board")
    public ResponseEntity<AnimalBoard> boardUpdate(@RequestBody AnimalBoardDTO dto) throws IOException {
        log.info("수정 dto : " + dto);
        Date nowDate = currentDate();
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
                    String image = matcher.group().substring(35, matcher.group().length() - 2); //image-resize에 안 들어간 경우
                    if(image.contains("\"")){
                        String[] finalizedArr = image.split("\"");
                        String finalizedImage = finalizedArr[0];
                        log.info("db에 저장될 이미지 : " + image);
                        log.info("2차 처리 이미지 배열: " + finalizedArr[0]);
                        log.info("2차 처리 이미지 최종본: " + finalizedImage);
                        images.add(finalizedImage);
                        // image-resize가 적용될 경우 split을 통해서 이미지 문자열만 2차 재추출 해야함.
                    }else{
                        images.add(image); // 매칭된 이미지들 images에 추가
                        // matcher 가 정규표현식이 매칭되는 이미지태그의 이미지를 iamges 리스트에 담아줌
                        // 얘가 DB에 저장되어야할 아이
                    }
                }
            }
            log.info("수정 후 이미지 리스트 : " + images);
            /* =================================================================================== */
            // animal_board_code 가 null 인 이미지 가져오기
            List<AnimalBoardImage> list = animalBoardService.viewImages(); // 여기에 userId=? 조건도 같이 붙으면 여러명이 써도 상관 x
            // 일단 그냥 막 들어와진 이미지들

            for(AnimalBoardImage image : list){
                log.info("imageg where code=null from db : " + image);
                log.info("새로 들어온 이미지 : " + image.getAnimalBoardImage());
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

            // 완성된 이미지 리스트 불러오기 - 추후에 리스트로 변경하여 사용자에게 선택권한을 줌
            AnimalBoardImage thumbnail = animalBoardService.getThumbnailList(response.getAnimalBoardCode());
            log.info("thumbnail : " + thumbnail);
            if(thumbnail!=null){
                animalBoardService.saveThumbnail(thumbnail.getAnimalBoardImage(), response);
            } else{
                animalBoardService.saveThumbnail("animalDefault.jpg", response);
            }

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    // 자유게시판 - 글 삭제
    @DeleteMapping("/animal-board/{animalBoardCode}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "animalBoardCode")int boardCode){
        // 삭제하기전 실제 파일 삭제.
        List<AnimalBoardImage> currentList = animalBoardService.getCurrentFiles(boardCode);
        for(AnimalBoardImage image : currentList){
            File file =new File(uploadPath+image.getAnimalBoardImage());
            file.delete();
        }
        animalBoardService.deleteBoard(boardCode);
        return ResponseEntity.ok().build();
    }

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
//        Date nowDate = currentDate();
//        log.info("now : " + nowDate);
        AnimalBoardFavorite favoriteBoard = AnimalBoardFavorite.builder()
                .animalBoard(AnimalBoard.builder()
                        .animalBoardCode(dto.getAnimalBoardCode())
                        .build())
                .user(User.builder()
                        .userId(dto.getUserId())
                        .build())
//                .animalFavoriteDate(nowDate)
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
    // 좋아요 수 가장 최신 업데이트전 수
    @GetMapping("/public/animal-board/{animalBoardCode}/latestFavCount")
    public ResponseEntity<Integer> latestFacCount(@PathVariable(name = "animalBoardCode") int boardCode){
        Integer latestCount = favoriteService.latestFavCount(boardCode);
        return latestCount != null ? ResponseEntity.ok(latestCount) : null;
    }
}
