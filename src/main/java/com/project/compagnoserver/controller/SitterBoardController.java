package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AnimalCategory;
import com.project.compagnoserver.domain.Parsing.LocationParsing;
import com.project.compagnoserver.domain.Parsing.LocationParsingDTO;
import com.project.compagnoserver.domain.SitterBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.SitterBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 6000, allowCredentials = "true")
public class SitterBoardController {

    @Autowired
    private SitterBoardService sitterBoardService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 카테고리 전체보기
    @GetMapping("public/sitter/category")
    public ResponseEntity<List<SitterCategory>> sitterCategoryView() {
        List<SitterCategory> sitterCategoryList = sitterBoardService.sitterCategoryView();
        return ResponseEntity.status(HttpStatus.OK).body(sitterCategoryList);
    }

    // 동물 카테고리 전체보기
    @GetMapping("public/sitter/animal-category")
    public ResponseEntity<List<AnimalCategory>> animalCategoryView() {
        List<AnimalCategory> animalCategoryList = sitterBoardService.animalCategoryView();
        return ResponseEntity.ok().body(animalCategoryList);
    }

    // 전체 보기
    @GetMapping("public/sitter")
    public ResponseEntity<Page<SitterBoard>> sitterViewAll(@RequestParam(name="sitterCategory", required = false) Integer sitterCateCode,
                                                           @RequestParam(name="animalCategory", required = false) Integer animalCateCode,
                                                           @RequestParam(name = "locationProvince", required = false) Integer provinceCode,
                                                           @RequestParam(name = "locationDistrict", required = false) Integer districtCode,
                                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                                           @RequestParam(name = "sortBy", defaultValue = "0") int sortBy) {

        // ================================ 검색 ================================
        QSitterBoard qSitterBoard = QSitterBoard.sitterBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;

        if(sitterCateCode!=null) {
            expression = qSitterBoard.sitterCategory.sitterCategoryCode.eq(sitterCateCode);
            builder.and(expression);
        }
        if(animalCateCode!=null) {
             expression = qSitterBoard.animalCategoryCode.animalCategoryCode.eq(animalCateCode);
             builder.and(expression);
        }
        if(provinceCode!=null){
            expression = qSitterBoard.location.parent.locationCode.eq(provinceCode);
            builder.and(expression);
        }
        if(districtCode!=null){
            expression = qSitterBoard.location.locationCode.eq(districtCode);
            builder.and(expression);
        }



        // ================================ 정렬 ================================
        Sort sort = null;
        switch (sortBy) {
            case 1: // 작성일 내림차순(최신순)
                sort = Sort.by("sitterRegiDate").descending();
                break;
            case 2: // 작성일 오름차순
                sort = Sort.by("sitterRegiDate").ascending();
                break;
            case 3: // 조회수 내림차순(조회순)
                sort = Sort.by("sitterViewCount").descending();
                break;
            case 4: // 조회수 오름차순
                sort = Sort.by("sitterViewCount").ascending();
                break;
            default:
                // 기본 정렬 설정: 최신순
                sort = Sort.by("sitterRegiDate").descending();
                break;
        }
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        Page<SitterBoard> sitterList = sitterBoardService.sitterViewAll(pageable, builder);
        return ResponseEntity.status(HttpStatus.OK).body(sitterList);
    }

    // 상세 보기
    @GetMapping("public/sitter/{code}")
    public ResponseEntity<SitterBoard> sitterView(@PathVariable("code") int code,
                                                  HttpServletRequest req, HttpServletResponse res) {
        SitterBoard sitterBoard = sitterBoardService.sitterView(code);
        sitterViewCount(code, req, res);
        return ResponseEntity.ok().body(sitterBoard);
    }


    // 조회수
    public void sitterViewCount(int code, HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElseGet(() -> new Cookie[0]);

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("sitterBoard"))
                .findFirst()
                .orElseGet(() -> {
                    sitterBoardService.sitterViewCount(code);
                    return new Cookie("sitterBoard", "[" + code + "]");
                });

        if(!cookie.getValue().contains("[" + code + "]")) {
            sitterBoardService.sitterViewCount(code);
            cookie.setValue(cookie.getValue() + "[" + code + "]");
        }
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        res.addCookie(cookie);
    }


    // 글 등록
    @PostMapping("sitter")
    public ResponseEntity<SitterBoard> sitterCreate(SitterBoardDTO sitterBoardDTO) throws IOException {

        Object principal = authentication();
        if(principal == null || !(principal instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Date now = new Date();
        log.info("dto : " + sitterBoardDTO);

        SitterBoard sitter = SitterBoard.builder()
                .animalCategoryCode(AnimalCategory.builder()
                        .animalCategoryCode(sitterBoardDTO.getAnimalCategoryCode()).build())
                .location(LocationParsing.builder()
                        .locationCode(sitterBoardDTO.getLocationCode()).build())
                .sitterCategory(SitterCategory.builder()
                        .sitterCategoryCode(sitterBoardDTO.getSitterCategory().getSitterCategoryCode()).build())
                .sitterTitle(sitterBoardDTO.getSitterTitle())
                .sitterContent(sitterBoardDTO.getSitterContent())
//                .user(User.builder().userNickname(((UserDetails) principal).getUsername()).build())
                .user(userInfo())
                .sitterRegiDate(now)
                .build();

        SitterBoard result = sitterBoardService.sitterCreate(sitter);

        if(sitterBoardDTO.getFiles() != null) {
            for (MultipartFile file : sitterBoardDTO.getFiles()) {
                String fileName = file.getOriginalFilename();
                SitterBoardImage sitterImg = new SitterBoardImage();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "sitterBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo((savePath));

                sitterImg.setSitterBoard(result);

                sitterImg.setSitterImg(saveName.substring(24));
                sitterBoardService.sitterCreateImg(sitterImg);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 글 수정
    @PutMapping("sitter")
    public ResponseEntity<SitterBoard> sitterUpdate(SitterBoardDTO sitterBoardDTO) throws IOException {

        Object principal = authentication();
        if(principal == null || !(principal instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 기존 이미지 삭제
        List<SitterBoardImage> uploadedimgs = sitterBoardService.sitterViewAllImg(sitterBoardDTO.getSitterBoardCode());
        for(SitterBoardImage image : uploadedimgs) {
            if((sitterBoardDTO.getImages()!=null && !sitterBoardDTO.getImages().contains(image.getSitterImg())) || sitterBoardDTO.getImages() == null) {
                File file = new File(image.getSitterImg());
                file.delete();
                log.info("이미지 삭제~");

                sitterBoardService.sitterDeleteImg(sitterBoardDTO.getSitterBoardCode());
            }
        }

        // 새로운 이미지 등록
        if(sitterBoardDTO.getFiles() != null) {
            for(MultipartFile file : sitterBoardDTO.getFiles()) {
                log.info("file~"  + file);
                SitterBoardImage sitterBoardImageVo = new SitterBoardImage();

                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = uploadPath + File.separator + "sitterBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                sitterBoardImageVo.setSitterImg(saveName.substring(24));
                sitterBoardImageVo.setSitterBoard(SitterBoard.builder().sitterBoardCode((sitterBoardDTO.getSitterBoardCode())).build());

                sitterBoardService.sitterCreateImg(sitterBoardImageVo);
            }
        }

        SitterBoard sitterBoard = sitterBoardService.sitterView(sitterBoardDTO.getSitterBoardCode());

        // 게시글 수정
        SitterBoard sitter = SitterBoard.builder()
                .sitterBoardCode(sitterBoardDTO.getSitterBoardCode())
                .animalCategoryCode(AnimalCategory.builder()
                        .animalCategoryCode(sitterBoardDTO.getAnimalCategoryCode()).build())
                .location(sitterBoard.getLocation())
                .sitterCategory(SitterCategory.builder()
                        .sitterCategoryCode(sitterBoardDTO.getSitterCategory().getSitterCategoryCode()).build())
                .sitterTitle(sitterBoardDTO.getSitterTitle())
                .sitterContent(sitterBoardDTO.getSitterContent())
                .sitterUpdateDate(sitterBoard.getSitterUpdateDate())
                .user(User.builder()
                        .userNickname(sitterBoardDTO.getUserNickname()).build())
                .build();

        sitterBoardService.sitterUpdate(sitter);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 글 삭제
    @Transactional
    @DeleteMapping("sitter/{code}")
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



//    ========================================== 북마크 ==========================================
    @PostMapping("sitter/bookmark")
    public ResponseEntity sitterBoardBookmark(@RequestBody SitterBoardBookmarkDTO sitterBoardBookmarkDTO) {
        SitterBoardBookmark bookmarkVo = new SitterBoardBookmark();
        bookmarkVo.setSitterBoard(SitterBoard.builder().sitterBoardCode(sitterBoardBookmarkDTO.getSitterBoardCode()).build());
        bookmarkVo.setUser(User.builder().userId(sitterBoardBookmarkDTO.getUserId()).build());
        sitterBoardService.sitterBoardBookmark(bookmarkVo);

        return ResponseEntity.ok().build();
    }


//    ========================================== 댓글 ==========================================

    // 댓글 추가
    @PostMapping("sitter/comment")
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
    @PutMapping("sitter/comment")
    public ResponseEntity<SitterBoardComment> sitterCommentUpdate(SitterCommentDTO sitterCommentDTO) {
        SitterBoardComment comment = sitterBoardService.sitterCommentview(sitterCommentDTO.getSitterCommentCode());

        comment.setSitterCommentCode(sitterCommentDTO.getSitterCommentCode());
        comment.setSitterCommentContent(sitterCommentDTO.getSitterCommentContent());

        sitterBoardService.sitterCommentUpdate(comment);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("sitter/comment/{commentCode}")
    public ResponseEntity sitterCommentDelete(@PathVariable("commentCode") int commentCode) {
        sitterBoardService.sitterCommentDelete(commentCode);
        return ResponseEntity.ok().build();
    }


    // 각 게시판에 대한 댓글 조회
    @GetMapping("public/sitter/{code}/comment")
    public ResponseEntity<List<SitterCommentDTO>> sitterViewAllComment(@PathVariable(name = "code") int code) {
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
                        .sitterCommentStatus(reply.getSitterCommentStatus())
                        .user(UserDTO.builder()
                                .userNickname(reply.getUser().getUserNickname())
                                .build())
                        .build();
                repliesDTO.add(dto);
            }

            SitterCommentDTO dto = SitterCommentDTO.builder()
                    .sitterBoardCode(top.getSitterBoardCode())
                    .sitterCommentCode(top.getSitterCommentCode())
                    .sitterCommentContent(top.getSitterCommentContent())
                    .sitterCommentRegiDate(top.getSitterCommentRegiDate())
                    .sitterCommentStatus(top.getSitterCommentStatus())
                    .user(UserDTO.builder()
                            .userNickname(top.getUser().getUserNickname())
                            .build())
                    .sitterReplies(repliesDTO)
                    .build();
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }


// ====================================== Authentication ======================================

    public Object authentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication != null ? authentication.getPrincipal() : null;
    }


// ====================================== Location ======================================

//    // 전체 시도/시군구 조회
//    @GetMapping("public/location")
//    public ResponseEntity<List<LocationParsingDTO>> viewAllLocation() {
//        List<LocationParsing> provinceList = sitterBoardService.getProvinces();
//        List<LocationParsingDTO> response = new ArrayList<>();
//
//        for(LocationParsing province : provinceList) {
//            List<LocationParsing> districtList = sitterBoardService.getDistricts(province.getLocationCode());
//
//            LocationParsingDTO dto = LocationParsingDTO.builder()
//                    .locationCode(province.getLocationCode())
//                    .locationName(province.getLocationName())
//                    .districts(districtList)
//                    .build();
//            response.add(dto);
//        }
//
//        return ResponseEntity.ok(response);
//    }

    // 시도 조회
    @GetMapping("public/location/province")
    public ResponseEntity<List<LocationParsing>> viewProvince() {
        return ResponseEntity.ok(sitterBoardService.sitterGetProvinces());
    }

    // 시도 선택에 따른 시군구 조회
    @GetMapping("public/location/district/{code}")
    public ResponseEntity<List<LocationParsingDTO>> viewDistrict(@PathVariable(name="code") int code) {
        List<LocationParsing> districts = sitterBoardService.sitterGetDistricts(code);
        List<LocationParsingDTO> districtsDTO = new ArrayList<>();

        for(LocationParsing district : districts) {
            LocationParsingDTO dto = LocationParsingDTO.builder()
                    .locationCode(district.getLocationCode())
                    .locationName(district.getLocationName())
                    .build();
            districtsDTO.add(dto);
        }
        return ResponseEntity.ok(districtsDTO);
    }

}
