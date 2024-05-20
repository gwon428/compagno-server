package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AnimalCategory;
import com.project.compagnoserver.domain.NeighborBoard.*;
import com.project.compagnoserver.domain.Parsing.LocationParsing;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.NeighborBoardService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/compagno/*")
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 6000, allowCredentials = "true")
public class NeighborBoardController {

    @Autowired
    private NeighborBoardService neighborBoardService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 동물 카테고리 전체보기
    @GetMapping("public/neighbor/animal-category")
    public ResponseEntity<List<AnimalCategory>> animalCategoryView() {
        List<AnimalCategory> animalCategoryList = neighborBoardService.animalCategoryView();
        return ResponseEntity.ok().body(animalCategoryList);
    }

    // 전체 보기
    @GetMapping("public/neighbor")
    public ResponseEntity<Page<NeighborBoard>> neighborViewAll(@RequestParam(name = "searchAnimal", required = false) Integer animalCateCode,
                                                               @RequestParam(name = "searchProvince", required = false) Integer provinceCode,
                                                               @RequestParam(name = "searchDistrict", required = false) Integer districtCode,
                                                               @RequestParam(name="searchSelect", required = false, defaultValue = "title") String searchSelect,
                                                               @RequestParam(name="title", required = false) String title,
                                                               @RequestParam(name="id", required = false) String id,
                                                               @RequestParam(name = "page", defaultValue = "1") int page,
                                                               @RequestParam(name = "sort", defaultValue = "0") int sortBy) {
        // ================================ 검색 ================================
        QNeighborBoard qNeighborBoard = QNeighborBoard.neighborBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;

        if(animalCateCode!=null && animalCateCode != 0) {
            expression = qNeighborBoard.animalCategoryCode.animalCategoryCode.eq(animalCateCode);
            builder.and(expression);
        }
        if(provinceCode!=null){
            expression = qNeighborBoard.location.parent.locationCode.eq(provinceCode);
            builder.and(expression);
        }

        if(districtCode!=null){
            expression = qNeighborBoard.location.locationCode.eq(districtCode);
            builder.and(expression);
        }

        if(title != null){
            expression = qNeighborBoard.neighborBoardTitle.like("%" + title + "%");
            builder.and(expression);
        }
        if(id != null){
            expression = qNeighborBoard.user.userId.like("%" + id + "%");
            builder.and(expression);
        }

        // ================================ 정렬 ================================
        Sort sort = null;
        switch (sortBy) {
            case 1: // 작성일 내림차순(최신순)
                sort = Sort.by("neighborBoardRegiDate").descending();
                break;
            case 2: // 작성일 오름차순
                sort = Sort.by("neighborBoardRegiDate").ascending();
                break;
            case 3: // 조회수 내림차순(조회순)
                sort = Sort.by("neighborBoardViewCount").descending();
                break;
            case 4: // 조회수 오름차순
                sort = Sort.by("neighborBoardViewCount").ascending();
                break;
            default:
                // 기본 정렬 설정: 최신순
                sort = Sort.by("neighborBoardRegiDate").descending();
                break;
        }
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        Page<NeighborBoard> list = neighborBoardService.neighborViewAll(pageable, builder);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    // 상세 페이지
    @GetMapping("public/neighbor/{code}")
    public ResponseEntity<NeighborBoard> neighborView(@PathVariable(name = "code") int code,
                                                      HttpServletRequest req, HttpServletResponse res) {
        NeighborBoard vo = neighborBoardService.neighborView(code);
        neighborViewCount(code, req, res);
        return ResponseEntity.ok().body(vo);
    }


    // 조회수
    public void neighborViewCount(int code, HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElseGet(() -> new Cookie[0]);

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("neighborBoard"))
                .findFirst()
                .orElseGet(() -> {
                    neighborBoardService.neighborViewCount(code);
                    return new Cookie("neighborBoard", "[" + code + "]");
                });

        if(!cookie.getValue().contains("[" + code + "]")) {
            neighborBoardService.neighborViewCount(code);
            cookie.setValue(cookie.getValue() + "[" + code + "]");
        }
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        res.addCookie(cookie);
    }


    // 게시글 등록
    @PostMapping("neighbor")
    public ResponseEntity<NeighborBoard> neighborCreate(NeighborBoardDTO neighborBoardDTO) throws IOException {

        Object principal = authentication();
        if(principal == null || !(principal instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Date now = new Date();

        // 게시글 작성
        NeighborBoard neighbor = NeighborBoard.builder()
                .animalCategoryCode(AnimalCategory.builder()
                        .animalCategoryCode(neighborBoardDTO.getAnimalCategoryCode()).build())
                .location(LocationParsing.builder()
                        .locationCode(neighborBoardDTO.getLocationCode()).build())
                .neighborBoardTitle(neighborBoardDTO.getNeighborBoardTitle())
                .neighborBoardContent(neighborBoardDTO.getNeighborBoardContent())
                .user(userInfo())
                .neighborBoardRegiDate(Timestamp.valueOf(neighborBoardDTO.getNeighborBoardRegiDate()))
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
                neighborImgVo.setNeighborImage(saveName.substring(24));
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

                neighborImgVo.setNeighborImage(saveName.substring(24));
                neighborImgVo.setNeighborBoard(NeighborBoard.builder().neighborBoardCode(neighborBoardDTO.getNeighborBoardCode()).build());

                neighborBoardService.neighborCreateImg(neighborImgVo);
            }
        }

        NeighborBoard neighborBoard = neighborBoardService.neighborView(neighborBoardDTO.getNeighborBoardCode());

        // 게시글 수정
        NeighborBoard neighbor = NeighborBoard.builder()
                .neighborBoardCode(neighborBoardDTO.getNeighborBoardCode())
                .animalCategoryCode(AnimalCategory.builder()
                        .animalCategoryCode(neighborBoardDTO.getAnimalCategoryCode()).build())
                .location(neighborBoard.getLocation())
                .neighborBoardTitle(neighborBoardDTO.getNeighborBoardTitle())
                .neighborBoardContent(neighborBoardDTO.getNeighborBoardContent())
                .neighborBoardUpdateDate(Timestamp.valueOf(neighborBoardDTO.getNeighborBoardUpdateDate()))
                .neighborBoardRegiDate(neighborBoard.getNeighborBoardRegiDate())
                .user(userInfo())
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


//    ========================================== 북마크 ==========================================
    @PostMapping("neighbor/bookmark")
    public ResponseEntity neighborBookmark(@RequestBody NeighborBoardBookmarkDTO neighborBoardBookmarkDTO) {
        NeighborBoardBookmark bookmarkVo = new NeighborBoardBookmark();
        bookmarkVo.setNeighborBoard(NeighborBoard.builder().neighborBoardCode(neighborBoardBookmarkDTO.getNeighborBookmarkCode()).build());
        bookmarkVo.setUser(User.builder().userId(neighborBoardBookmarkDTO.getUserId()).build());
        neighborBoardService.neighborBookmark(bookmarkVo);

        return ResponseEntity.ok().build();
    }



//    ========================================== 댓글 ==========================================

    // 댓글 추가
    @PostMapping("neighbor/comment")
    public ResponseEntity<NeighborBoardComment> neighborCommentCreate(@RequestBody NeighborBoardCommentDTO dto) {
//        Object principal = authentication();
//
//        if(principal instanceof User) {
//            User user = (User) principal;
//            neighborBoardCommentVo.setUser(user);
//            return ResponseEntity.ok(neighborBoardService.neighborCommentCreate(neighborBoardCommentVo));
//        }
//
//        return ResponseEntity.badRequest().build();

        NeighborBoardComment vo = NeighborBoardComment.builder()
                .neighborBoard(NeighborBoard.builder()
                        .neighborBoardCode(dto.getNeighborBoardCode())
                        .build())
                .neighborCommentCode(dto.getNeighborCommentCode())
                .neighborCommentContent(dto.getNeighborCommentContent())
                .neighborCommentParentCode(dto.getNeighborCommentParentCode())
                .user(User.builder()
                        .userId(userInfo().getUserId())
                        .build())
                .build();
        neighborBoardService.neighborCommentCreate(vo);
        return ResponseEntity.ok().build();
    }

    // 댓글 수정
    @PutMapping("neighbor/comment")
    public ResponseEntity<NeighborBoardComment> neighborCommentUpdate(@RequestBody NeighborBoardCommentDTO neighborBoardCommentDTO) {

//        NeighborBoardComment comment = neighborBoardService.neighborCommentview(neighborBoardCommentDTO.getNeighborCommentCode());
//
//        comment.setNeighborCommentCode(neighborBoardCommentDTO.getNeighborCommentCode());
//        comment.setNeighborCommentContent(neighborBoardCommentDTO.getNeighborCommentContent());

        NeighborBoardComment comment = NeighborBoardComment.builder()
                .neighborBoard(NeighborBoard.builder()
                        .neighborBoardCode(neighborBoardCommentDTO.getNeighborBoardCode())
                        .build())
                .neighborCommentCode(neighborBoardCommentDTO.getNeighborCommentCode())
                .neighborCommentContent(neighborBoardCommentDTO.getNeighborCommentContent())
                .user(User.builder()
                        .userId(userInfo().getUserId())
                        .build())
                .build();

        neighborBoardService.neighborCommentUpdate(comment);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("neighbor/comment/{commentCode}")
    public ResponseEntity neighborCommentDelete(@PathVariable("commentCode") int commentCode) {
        neighborBoardService.neighborCommentDelete(commentCode);
        return ResponseEntity.ok().build();
    }

    // 각 게시판에 대한 댓글 조회
    @GetMapping("public/neighbor/{code}/comment")
    public ResponseEntity<List<NeighborBoardCommentDTO>> neighborViewAllComment(@PathVariable(name = "code") int code) {
        List<NeighborBoardComment> topList = neighborBoardService.getTopComments(code);
        List<NeighborBoardCommentDTO> response = new ArrayList<>();

        for(NeighborBoardComment top : topList) {
            List<NeighborBoardComment> replies = neighborBoardService.getReplyComments(top.getNeighborCommentCode(), code);

            List<NeighborBoardCommentDTO> repliesDTO = new ArrayList<>();

            for(NeighborBoardComment reply : replies) {
                NeighborBoardCommentDTO dto = NeighborBoardCommentDTO.builder()
                        .neighborBoardCode(reply.getNeighborBoard().getNeighborBoardCode())
                        .neighborCommentCode(reply.getNeighborCommentCode())
                        .neighborCommentContent(reply.getNeighborCommentContent())
                        .neighborCommentRegiDate(reply.getNeighborCommentRegiDate())
//                        .user(User.builder()
//                                .userNickname(reply.getUser().getUserNickname())
//                                .build())
                        .user(reply.getUser())
                        .build();
                repliesDTO.add(dto);
            }

            NeighborBoardCommentDTO dto = NeighborBoardCommentDTO.builder()
                    .neighborBoardCode(top.getNeighborBoard().getNeighborBoardCode())
                    .neighborCommentCode(top.getNeighborCommentCode())
                    .neighborCommentContent(top.getNeighborCommentContent())
                    .neighborCommentRegiDate(top.getNeighborCommentRegiDate())
//                    .user(User.builder()
//                            .userNickname(top.getUser().getUserNickname())
//                            .build())
                    .user(top.getUser())
                    .neighborReplies(repliesDTO)
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

    // 시도 조회
    @GetMapping("public/neighbor/province")
    public ResponseEntity<List<NeighborBoardLocation>> neighborViewProvince() {
        return ResponseEntity.ok(neighborBoardService.neighborGetProvinces());
    }

    // 시도 선택에 따른 시군구 조회
    @GetMapping("public/neighbor/district/{code}")
    public ResponseEntity<List<NeighborBoardLocationDTO>> neighborViewDistrict(@PathVariable(name="code") int code) {
        List<NeighborBoardLocation> districts = neighborBoardService.neighborGetDistricts(code);
        List<NeighborBoardLocationDTO> districtsDTO = new ArrayList<>();

        for(NeighborBoardLocation district : districts) {
            NeighborBoardLocationDTO dto = NeighborBoardLocationDTO.builder()
                    .locationCode(district.getLocationCode())
                    .locationName(district.getLocationName())
                    .build();
            districtsDTO.add(dto);
        }
        return ResponseEntity.ok(districtsDTO);
    }

}
