package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.LostBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.LostBoardCommentService;
import com.project.compagnoserver.service.LostBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class LostBoardController {

    @Autowired
    private LostBoardService service;

    @Autowired
    private LostBoardCommentService comment;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 추가 --------------------------------------------------------------------
    @PostMapping("/lostBoard")
    public ResponseEntity<LostBoard> create(LostBoardDTO dto) throws IOException {

        LostBoard lost = new LostBoard();
//        lost.setLostBoardCode(dto.getLostBoardCode());
        lost.setUserId(dto.getUserId());
        lost.setUserImg(dto.getUserImg());
        lost.setUserNickname(dto.getUserNickname());
        lost.setUserPhone(dto.getUserPhone());
//        lost.setLostTitle(dto.getLostTitle());
//        lost.setLostAnimalImage(dto.getLostAnimalImage());
        lost.setLostAnimalName(dto.getLostAnimalName());
        lost.setLostDate(dto.getLostDate());
        lost.setLostLocation(dto.getLostLocation());
        lost.setLostLocationDetail(dto.getLostLocationDetail());
        lost.setLostAnimalKind(dto.getLostAnimalKind());
        lost.setLostAnimalColor(dto.getLostAnimalColor());
        lost.setLostAnimalGender(dto.getLostAnimalGender());
        lost.setLostAnimalAge(dto.getLostAnimalAge());
        lost.setLostAnimalFeature(dto.getLostAnimalFeature());
        lost.setLostAnimalRFID(dto.getLostAnimalRFID());
        lost.setLostRegiDate(dto.getLostRegiDate());

        LostBoard result = service.create(lost);

        if(dto.getImages()!=null){
            for(MultipartFile file : dto.getImages()){
                if(!file.getOriginalFilename().equals("")){
                    LostBoardImage images = new LostBoardImage();

                    String fileName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "lostBoard" + File.separator + uuid + "_" + fileName;
                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

                    if(result.getLostAnimalImage() == null){
                        result.setLostAnimalImage(saveName);
                        log.info("result.대표사진 : ");
                    }
                    
                    images.setLostImage(saveName);
                    images.setLostBoardCode(result);
                    
                    service.createImages(images);
                }
            }
        }
        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 조회수 관련
@PutMapping("public/lostBoard/viewCount")
public ResponseEntity<LostBoard> updateViewCount(@RequestParam(name="lostBoardCode", defaultValue = "0") int lostBoardCode){
    log.info("lostBoardCode : " + lostBoardCode);
    service.updateView(lostBoardCode);
    return ResponseEntity.ok().build();
}


    // 전체 보기------------------------------------------------------------------------------
    // 검색 : 종류(전체, 강아지, 고양이, 그외) / 성별 / 작성자 닉네임 / 분실 날짜 / 분실 지역 / 분실 동물 이름
    // 정렬 : 분실 날짜순(오래된순, 최신순) / 조회수순(높은순, 낮은순) / 작성일순(오래된순/최신순)
    @GetMapping("/public/lostBoard")
    public ResponseEntity<Page<LostBoard>> viewAll(@RequestParam(name="page", defaultValue = "1")int page,
                                                   @RequestParam(name="lostAnimalKind", required = false)String lostAnimalKind,
                                                   @RequestParam(name="lostAnimalGender", required = false)String lostAnimalGender,
                                                   @RequestParam(name="userNickname", required = false) String userNickname,
                                                   @RequestParam(name="lostDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lostDate,
                                                   @RequestParam(name="lostLocation", required = false)String lostLocation,
                                                   @RequestParam(name="lostAnimalName", required = false)String lostAnimalName,
                                                   @RequestParam(name="sort", defaultValue = "0")int sortNum){
//        Sort sort = Sort.by("lostBoardCode").descending();
//        Pageable pageable = PageRequest.of(page-1, 12, sort);
        // 기존에는 List<LostBoard>  // body(list.getContent)였음
        QLostBoard qLostBoard = QLostBoard.lostBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = null;
        if(lostAnimalKind != null){
            expression = qLostBoard.lostAnimalKind.contains(lostAnimalKind);
            builder.and(expression);
        }
        if(lostAnimalGender != null){
            expression = qLostBoard.lostAnimalGender.contains(lostAnimalGender);
            builder.and(expression);
        }
        if(userNickname != null){
            expression = qLostBoard.userNickname.contains(userNickname);
            builder.and(expression);
        }
        if(lostDate != null){
            expression = qLostBoard.lostDate.eq(lostDate);
            builder.and(expression);
        }
        if(lostLocation != null){
            expression = qLostBoard.lostLocation.contains(lostLocation);
            builder.and(expression);
        }
        if(lostAnimalName != null){
            expression = qLostBoard.lostAnimalName.contains(lostAnimalName);
            builder.and(expression);
        }


        Pageable pageable = PageRequest.of(page-1, 12);

        // 작성일 순(선입, 후입)
        Sort lRegiDateD = Sort.by("lostBoardCode").descending();
        Sort lRegiDate = Sort.by("lostRegiDate");


        if(sortNum==0){
            pageable = PageRequest.of(page-1, 12, lRegiDateD);
        }
        if(sortNum==1){
            pageable = PageRequest.of(page-1, 12, lRegiDate);
        }


        // 분실 날짜 순(선입, 후입)
        if(sortNum==2){
            Sort lDateD = Sort.by("lostDate").descending();
            pageable = PageRequest.of(page-1, 12, lDateD);
        }
        if(sortNum==3){
            Sort lDate = Sort.by("lostDate");
            pageable = PageRequest.of(page-1, 12, lDate);
        }


        // 조회수순(낮은 순, 높은 순)
        Sort lViewCount = Sort.by("lostViewCount");
        Sort lViewCountD = Sort.by("lostViewCount").descending();

        if(sortNum==4){
            pageable = PageRequest.of(page-1, 12, lViewCount);
        }
        if(sortNum==5){
            pageable = PageRequest.of(page-1, 12, lViewCountD);
        }

        return ResponseEntity.ok(service.viewAll(pageable, builder));
    }

    // 하나 보기 --------------------
    @GetMapping("/public/lostBoard/{lostBoardCode}")
    public ResponseEntity<LostBoard> view(@PathVariable(name="lostBoardCode")int lostBoardCode){
        LostBoard lost = service.view(lostBoardCode);
        return ResponseEntity.status(HttpStatus.OK).body(lost);
    }
    
    // 사진 수정(프론트에 맞춰 변경) 
    // 수정 ---------------------------------------------------------------------------------------
    @PutMapping("/lostBoard")
    public ResponseEntity<LostBoard> update(LostBoardDTO dto) throws IOException {
        log.info("dto : " + dto);

//        LostBoard prev = service.view(dto.getLostBoardCode());
//        LostBoard lost = service.view(dto.getLostBoardCode());;

        List<LostBoardImage> list = service.findByCode(dto.getLostBoardCode());

        List<String> mainImage = new ArrayList<>();

        LostBoard lost = LostBoard.builder()
                .lostBoardCode(dto.getLostBoardCode())
                .userId(dto.getUserId())
                .userImg(dto.getUserImg())
                .userNickname(dto.getUserNickname())
                .userPhone(dto.getUserPhone())
                .lostAnimalName(dto.getLostAnimalName())
                .lostDate(dto.getLostDate())
                .lostLocation(dto.getLostLocation())
                .lostLocationDetail(dto.getLostLocationDetail())
                .lostAnimalKind(dto.getLostAnimalKind())
                .lostAnimalColor(dto.getLostAnimalColor())
                .lostAnimalGender(dto.getLostAnimalGender())
                .lostAnimalAge(dto.getLostAnimalAge())
                .lostAnimalFeature(dto.getLostAnimalFeature())
                .lostAnimalRFID(dto.getLostAnimalRFID())
                .lostRegiDate(dto.getLostRegiDate())
                .build();

        for(LostBoardImage image : list){

            if(dto.getImage()!=null&&!dto.getImage().contains(image.getLostImage()) || dto.getImage()==null) {
                File file = new File(image.getLostImage());
                file.delete();
                service.deleteImage(image.getLostImageCode());
            } else {
                mainImage.add(image.getLostImage());
            }
        }

        if(dto.getImages()!=null){

            for(LostBoardImage image : list){
                File file = new File(image.getLostImage());
                file.delete();
                service.deleteImage(image.getLostImageCode());
                mainImage.remove(0);
            }
            for(MultipartFile file : dto.getImages()){

                String fileName =  file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();

                String saveName = uploadPath + File.separator + "lostBoard" + File.separator + uuid + "_" + fileName;
                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

                    service.createImages(LostBoardImage.builder()
                            .lostImage(saveName)
                            .lostBoardCode(LostBoard.builder().lostBoardCode(dto.getLostBoardCode()).build())
                            .build());

                mainImage.add(saveName);

            }

//            log.info(vo.getLostImage());
            log.info(dto.getImages().toString());
        }
        lost.setLostAnimalImage(mainImage.getFirst());

        service.create(lost);

    return ResponseEntity.ok().build();
//        return result!=null?
//                ResponseEntity.status(HttpStatus.CREATED).body(result):
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }



    // 삭제 ------------------------------------------
    @DeleteMapping("/lostBoard/{lostBoardCode}")
    public ResponseEntity<LostBoard> delete(@PathVariable(name="lostBoardCode")int lostBoardCode){

        LostBoard lost = service.view(lostBoardCode);
        if(lost!=null){

            service.imageDelete(lostBoardCode);
            service.delete(lostBoardCode);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    //[댓글 관련 로직]---------------------------------------------
    @PostMapping("/lostBoard/comment")
    public ResponseEntity createComment(@RequestBody LostBoardComment vo){
        log.info("vov : " + vo.getUserNickname());

        // 시큐리티 담은 로그인한 사용자의 정보 가져오기
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        log.info("principal : " + principal);

        if(principal instanceof User){
            User user = (User) principal;
            vo.setUser(user);
            vo.setUserNickname(user.getUserNickname());
            vo.setUserImg(user.getUserImg());

            return ResponseEntity.ok().body(comment.create(vo));
        }
        return ResponseEntity.badRequest().build();
    }

    // 게시물 1개 따른 댓글 조회
    @GetMapping("/public/lostBoard/comment/{lostBoardCode}")
    public ResponseEntity<List<LostBoardCommentDTO>> viewComment(@PathVariable(name="lostBoardCode")int lostBoardCode, @RequestParam(name="page")int page){
        List<LostBoardComment> topList = comment.topComments(lostBoardCode, page);
        List<LostBoardCommentDTO> response = commentDetailList(topList, lostBoardCode);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/public/lostBoard/commentAll/{lostBoardCode}")
    public ResponseEntity<List<LostBoardCommentDTO>> viewComment(@PathVariable(name="lostBoardCode")int lostBoardCode){
        List<LostBoardComment> topList = comment.topAllComments(lostBoardCode);
        List<LostBoardCommentDTO> response = commentDetailList(topList, lostBoardCode);
        return ResponseEntity.ok(response);
    }

    // 나머지 공통 부분 빼기
    public List<LostBoardCommentDTO> commentDetailList(List<LostBoardComment> comments, int lostBoardCode){
        List<LostBoardCommentDTO> response = new ArrayList<>();

        for(LostBoardComment item : comments){
            log.info("item : " + item );
            List<LostBoardComment> replies = comment.bottomComments(item.getLostCommentCode(), lostBoardCode);
            List<LostBoardCommentDTO> repliesDTO = commentDetailList(replies, lostBoardCode);
            LostBoardCommentDTO dto = commentDetail(item);
            dto.setReplies(repliesDTO);
            log.info("dto : " + dto);
            response.add(dto);
        }
        return response;
    }

    // builder.build 공통부분 빼기
    public LostBoardCommentDTO commentDetail(LostBoardComment vo){
    log.info("vo : " + vo.getLostBoardCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String enrollDate = sdf.format(vo.getCommentDate());
        return LostBoardCommentDTO.builder()
                .lostBoardCode(vo.getLostBoardCode())
                .commentDate(sdf.format(vo.getCommentDate()))
                .commentContent(vo.getCommentContent())
                .lostCommentCode(vo.getLostCommentCode())
                .user(UserDTO.builder()
                        .userId(vo.getUser().getUserId())
                        .userNickname(vo.getUserNickname())
                        .userImg(vo.getUserImg())
                        .build())
                .build();
    }

    // 수정
    @PutMapping("/lostBoard/comment")
    public ResponseEntity<LostBoardComment> updateComment(@RequestBody LostBoardCommentDTO dto){

        LostBoardComment vo = comment.viewComment(dto.getLostCommentCode());
        vo.setCommentDate(Timestamp.valueOf(dto.getCommentDate()));
        vo.setCommentContent(dto.getCommentContent());
        LostBoardComment result = comment.update(vo);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(result)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 삭제
    @DeleteMapping("/lostBoard/comment/{lostCommentCode}")
    public ResponseEntity<LostBoardComment> deleteComment(@PathVariable(name="lostCommentCode") int lostCommentCode){
    log.info("lostCommentCode : " + lostCommentCode);

        LostBoardComment vo = comment.viewComment(lostCommentCode);
        log.info("vo:" + vo.getCommentContent());
        if(vo!=null){
            comment.deleteComment(lostCommentCode);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
