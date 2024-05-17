package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.UserQnaBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.UserQnaAnswerBoardService;
import com.project.compagnoserver.service.UserQnaQuestionBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController @CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/compagno/*")
@Slf4j
public class UserQnaBoardController {
    @Autowired
    private UserQnaQuestionBoardService service;

    @Autowired
    private UserQnaAnswerBoardService answerService;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // Question =============================================================================================
    // 1. 질문 등록
    @PostMapping("/userQuestion")
    public ResponseEntity<UserQnaQuestionBoard> create(UserQnaQuestionBoardDTO dto) throws IOException {
        log.info("dto : " + dto);

        // dto로 받은 값을 builder를 통해 vo 형태로 변환
//        UserQnaQuestionBoard vo = new UserQnaQuestionBoard();

//        vo.setUserId(dto.getUserId());
//        vo.setUserNickname(dto.getUserNickname());
//        vo.setUserImg(dto.getUserImg());

//        vo.setUserQuestionBoardTitle(dto.getUserQuestionBoardTitle());
//        vo.setUserQuestionBoardContent(dto.getUserQuestionBoardContent());


        UserQnaQuestionBoard result = service.create(UserQnaQuestionBoard.builder()
                        .userId(dto.getUserId())
                        .userNickname(dto.getUserNickname())
                        .userImg(dto.getUserImg())
                        .userQuestionBoardStatus('N')
                        .animalCategoryCode(dto.getAnimalCategoryCode())
                        .userQuestionBoardTitle(dto.getUserQuestionBoardTitle())
                        .userQuestionBoardContent(dto.getUserQuestionBoardContent())
                .build());

        if(dto.getFiles() != null){
            for(MultipartFile file : dto.getFiles()){
                if(file.getOriginalFilename() != ""){
                    UserQnaQuestionBoardImage img = new UserQnaQuestionBoardImage();

                    String fileName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "userQuestion" + File.separator + uuid + "_" + fileName;

                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);
//                    img.setUserQuestionImgUrl(saveName.substring(27));
                    img.setUserQuestionImgUrl(saveName.substring(38));
                    img.setUserQuestionBoardCode(result.getUserQuestionBoardCode());

                    service.createImg(img);
                }
            }
        }
        return ResponseEntity.ok()
                .build();
    }

    // 2. 리스트 출력
    @GetMapping("/public/userQuestion")
    public ResponseEntity<Page<UserQnaQuestionBoard>> viewAll(@RequestParam(name="title", required=false) String title,
                                                              @RequestParam(name="content", required = false) String content,
                                                              @RequestParam(name="id", required = false) String id,
                                                              @RequestParam(name="category", required = false) String category,
                                                              @RequestParam(name="status", required = false) String status,
                                                              @RequestParam(name="sort", defaultValue = "1") int sortval,
                                                              @RequestParam(name="page", defaultValue = "1") int page){

        Pageable pageable = PageRequest.of(page-1, 10);

        // 최신 등록순
        Sort sortregisterdesc = Sort.by("userQuestionBoardCode").descending();

        // 오래된 순
        Sort sortregisterasc = Sort.by("userQuestionBoardCode").ascending();

        // 답변 많은순
        Sort sortanswers = Sort.by("userQuestionBoardCount").descending();

        // 좋아요순
        Sort likecounts = Sort.by("likecount").descending();

        // 조회순
        Sort viewcounts = Sort.by("viewcount").descending();

        if(sortval !=0){
        if(sortval == 1){
            pageable = PageRequest.of(page-1, 10, sortregisterdesc);
        }
        if(sortval == 2){
            pageable = PageRequest.of(page-1, 10, sortregisterasc);
        }
        if(sortval == 3){
            pageable = PageRequest.of(page-1, 10, sortanswers);
        }
        if(sortval == 4){
            pageable = PageRequest.of(page-1, 10, viewcounts);
        }
        if(sortval == 5){
            pageable = PageRequest.of(page-1, 10, viewcounts);
        }
        }


        QUserQnaQuestionBoard qUserQnaQuestionBoard = QUserQnaQuestionBoard.userQnaQuestionBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;

        /*
        * SELECT * FROM
        * */
        if(title != null){
            log.info("title : ");
            expression = qUserQnaQuestionBoard.userQuestionBoardTitle.like("%" + title + "%");
            builder.or(expression);
        }
        if(id != null){
            log.info("id : ");
            expression = qUserQnaQuestionBoard.userId.like("%" + id + "%");
            builder.and(expression);
        }
        if(content != null){
            log.info("content : ");
            expression = qUserQnaQuestionBoard.userQuestionBoardContent.like("%" + content + "%");
            builder.and(expression);
        }

        if(category != null){
            log.info("category : ");
            switch(category){
                case "1":
                    expression = qUserQnaQuestionBoard.animalCategoryCode.eq(1);
                    builder.and(expression);
                    break;
                case "2":
                    expression = qUserQnaQuestionBoard.animalCategoryCode.eq(2);
                    builder.and(expression);
                    break;
                case "3":
                    expression = qUserQnaQuestionBoard.animalCategoryCode.eq(3);
                    builder.and(expression);
                    break;
            }
        }

        if(status != null){
            switch(status){
                case "1" :
                    expression = qUserQnaQuestionBoard.userQuestionBoardStatus.eq('Y');
                    log.info("status : " + status);
                    builder.and(expression);
                    break;

                case "2":
                    expression = qUserQnaQuestionBoard.userQuestionBoardStatus.eq('N');
                    builder.and(expression);
                    break;
            }
        }

        Page<UserQnaQuestionBoard> list = service.viewAll(builder, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 좋아요한 글 리스트 보기
    @GetMapping("/userQuestion")
    public ResponseEntity<Page<UserQnaQuestionBoard>> viewliked(@RequestParam(name="liked", defaultValue = "false") boolean liked,
                                                                @RequestParam(name="page", defaultValue = "1") int page){

        Sort sort = Sort.by("userQuestionBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        QUserQnaQuestionBoard qUserQnaQuestionBoard = QUserQnaQuestionBoard.userQnaQuestionBoard;
        QUserQnaQuestionLike qUserQnaQuestionLike = QUserQnaQuestionLike.userQnaQuestionLike;

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        BooleanBuilder builder = new BooleanBuilder();

        //SELECT * FROM user_question_board
        //JOIN user_question_like USING (user_question_board_code)
        //WHERE user_question_like.user_id = "user0002";
        if(principal instanceof User) {
            User user = (User) principal;
            if(liked){
                List<UserQnaQuestionBoard> list = queryFactory
                        .selectFrom(qUserQnaQuestionBoard)
                        .join(qUserQnaQuestionLike).on(qUserQnaQuestionBoard.userQuestionBoardCode.eq(qUserQnaQuestionLike.userQuestionBoardCode))
                        .where(qUserQnaQuestionLike.userId.eq(user.getUserId()))
                        .fetch();


                log.info("list :" + list);

                Page<UserQnaQuestionBoard> likedlist = service.viewliked(list, pageable);
                log.info("likedlist : " + likedlist);
                return ResponseEntity.status(HttpStatus.OK).body(likedlist);
            } else{
                return ResponseEntity.status(HttpStatus.OK).body(service.viewAll(builder, pageable));
            }

        }
        return null;
    }

    // 3-0. 링크 클릭 시 조회수 업데이트
    @PutMapping("/public/userQuestion/{code}")
    public ResponseEntity<UserQnaQuestionBoard> updateviewcount (@PathVariable(name="code") int code){
        service.updateviewcount(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 3. 상세보기
    @GetMapping("/public/userQuestion/{code}")
    public ResponseEntity<UserQnaQuestionBoardDTO> view (@PathVariable(name="code") int code){


        UserQnaQuestionBoard result = service.view(code);

        UserQnaQuestionBoardDTO dto = UserQnaQuestionBoardDTO.builder()
                .userQuestionBoardCode(result.getUserQuestionBoardCode())
                .userQuestionBoardTitle(result.getUserQuestionBoardTitle())
                .userQuestionBoardContent(result.getUserQuestionBoardContent())
                .userQuestionBoardstatus(result.getUserQuestionBoardStatus())
                .viewcount((result.getViewcount()))
                .animalCategoryCode(result.getAnimalCategoryCode())
                .userId(result.getUserId())
                .userNickname(result.getUserNickname())
                .userImg(result.getUserImg())
                .images(service.viewImg(code))
                .build();

        log.info("dtoimg : "+ result.getUserImg());
        log.info("조회수 : " + result.getViewcount());

        if(result.getUserQuestionBoardDate() == null){
            dto.setUserQuestionBoardDate(result.getUserQuestionBoardDateUpdate());
        } else {
            dto.setUserQuestionBoardDate(result.getUserQuestionBoardDate());
        }

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // 4. 수정하기
    @PutMapping("/userQuestion")
    public ResponseEntity update(UserQnaQuestionBoardDTO dto) throws IOException {
        if(dto.getImages() != null){
            List<String> imageList = dto.getImages().stream().map(image -> image.getUserQuestionImgUrl()).collect(Collectors.toList());

            List<UserQnaQuestionBoardImage> list = service.viewImg(dto.getUserQuestionBoardCode());

            log.info("list : " + list);
            // 이전 이미지 삭제
            for(UserQnaQuestionBoardImage image : list){
                if((dto.getImages() != null && !imageList.contains(image.getUserQuestionImgUrl()) || (dto.getImages() == null))){
                    log.info("dto : " + dto.getImages());
                    File file = new File(image.getUserQuestionImgUrl());
                    file.delete();

                    service.deleteImg(image.getUserQuestionImgCode());
                }
            }
        } else {
            List<UserQnaQuestionBoardImage> list = service.viewImg(dto.getUserQuestionBoardCode());
            for (UserQnaQuestionBoardImage image : list) {
                File file = new File(image.getUserQuestionImgUrl());
                file.delete();

                service.deleteImg(image.getUserQuestionImgCode());
            }
        }

            if(dto.getFiles() != null) {
                for (MultipartFile file : dto.getFiles()) {
                    UserQnaQuestionBoardImage img = new UserQnaQuestionBoardImage();

                    String fileName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "userQuestion" + File.separator + uuid + "_" + fileName;

                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

//                    img.setUserQuestionImgUrl(saveName.substring(27));
                    img.setUserQuestionImgUrl(saveName.substring(38));
                    img.setUserQuestionBoardCode(dto.getUserQuestionBoardCode());

                    service.createImg(img);
                }
            }

                // 추가하는 이미지가 비어있지 않을 때
                UserQnaQuestionBoard vo = UserQnaQuestionBoard.builder()
                        .userId(dto.getUserId())
                        .userNickname(dto.getUserNickname())
                        .userImg(dto.getUserImg())

                        .userQuestionBoardCode(dto.getUserQuestionBoardCode())
                        .userQuestionBoardTitle(dto.getUserQuestionBoardTitle())
                        .userQuestionBoardContent(dto.getUserQuestionBoardContent())
                        .userQuestionBoardStatus('N')
                        .build();

                service.update(vo);

                return ResponseEntity.ok().build();
    }

    // 5. 삭제하기
    @DeleteMapping("/userQuestion/{code}")
    public ResponseEntity<UserQnaQuestionBoardDTO> delete(@PathVariable(name="code")int code){
        // 해당 질문 관련 이미지 찾아서 리스트로 출력
        List<UserQnaQuestionBoardImage> list = service.viewImg(code);

        // 이미지가 존재하는 경우 그 이미지 파일 삭제
        if(list != null){
            for(UserQnaQuestionBoardImage item : list){
                File file = new File(item.getUserQuestionImgUrl());
                file.delete();

                // DB에서도 정보들 삭제
                service.deleteImg(item.getUserQuestionImgCode());
            }
        }
        // 질문 삭제
        service.delete(code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 6-1. 답변 채택
    @PostMapping("/userQuestion/answerChoose")
    public ResponseEntity<UserQnaAnswerChoose> chooseAnswer(UserQnaAnswerChoose vo){
        service.chooseAnswer(vo);

        // 채택 시 채택 상태 "Y"로 변경
        UserQnaQuestionBoard result = service.view(vo.getUserQuestionBoardCode());
        result.setUserQuestionBoardStatus('Y');
        service.update(result);
        return null;
    }

    // 6-2. 채택 취소하기
    @DeleteMapping("/userQuestion/answerChoose/{code}")
    public void deleteChoose(@PathVariable(name="code") int code){
        UserQnaAnswerChoose chooseAnswer = service.getChoose(code);
        log.info("취소할 답변 : " + chooseAnswer);

        // 취소 시 채택 상태 N으로 변경
        UserQnaQuestionBoard result = service.view(code);
        result.setUserQuestionBoardStatus('N');
        service.update(result);
        service.deleteChoose(chooseAnswer.getChooseCode());
    }

    // 6-3. 채택된 답변 보기!
    @GetMapping("/public/userQuestion/answerChoose/{code}")
    public ResponseEntity<UserQnaAnswerBoardDTO> getAnswer(@PathVariable(name="code") int code){
        // questionBoardCode로 choose 찾아서 그와 연결된 answer 출력
        UserQnaAnswerChoose choose = service.getChoose(code);
        UserQnaAnswerBoard chooseAnswer;
        if(choose != null){
             chooseAnswer = answerService.viewAnswer(choose.getUserAnswerBoardCode());
        } else {
             chooseAnswer= null;
             return ResponseEntity.status(HttpStatus.OK).build();
        }


        List<UserQnaAnswerBoardDTO> response = new ArrayList<>();
        // 상위 댓글의 revi 코드를 통해 하위 댓글 리스트 가져오기
        List<UserQnaAnswerBoard> topChooseanswers = answerService.getBottomLevelAnswers(chooseAnswer.getUserAnswerBoardCode(), code);

        // 하위 댓글을 dto를 통해 가공
        for(UserQnaAnswerBoard answer : topChooseanswers){

            UserQnaAnswerBoardDTO dto = UserQnaAnswerBoardDTO.builder()
                    .userQuestionBoardCode(answer.getUserQuestionBoardCode())
                    .userAnswerBoardCode(answer.getUserAnswerBoardCode())
                    .user(UserDTO.builder()
                            .userId(answer.getUser().getUserId())
                            .userNickname(answer.getUserNickname())
                            .userImg(answer.getUserImg())
                            .build())
                    .userAnswerContent(answer.getUserAnswerContent())
                    .userAnswerDate(answer.getUserAnswerDateUpdate())
                    .userAnswerDateUpdate(answer.getUserAnswerDateUpdate())
                    .build();
            response.add(dto);
        }

        UserQnaAnswerBoardDTO dto = UserQnaAnswerBoardDTO.builder()
                .userQuestionBoardCode(chooseAnswer.getUserQuestionBoardCode())
                .userAnswerBoardCode(chooseAnswer.getUserAnswerBoardCode())
                .user(UserDTO.builder()
                        .userId(chooseAnswer.getUser().getUserId())
                        .userNickname(chooseAnswer.getUserNickname())
                        .userImg(chooseAnswer.getUserImg())
                        .build())
                .userNickname(chooseAnswer.getUserNickname())
                .userImg(chooseAnswer.getUserImg())
                .userAnswerContent(chooseAnswer.getUserAnswerContent())
                .userAnswerDate(chooseAnswer.getUserAnswerDate())
                .userAnswerDateUpdate(chooseAnswer.getUserAnswerDateUpdate())
                .answers(response)
                .build();

        return dto != null ? ResponseEntity.status(HttpStatus.OK).body(dto) : ResponseEntity.status(HttpStatus.OK).build();
    }

    // 7-1. 좋아요 등록하기
    @PostMapping("/userQuestion/like")
    public ResponseEntity addLike(UserQnaQuestionLike like){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        if(principal instanceof User) {
            User user = (User) principal;

            UserQnaQuestionLike vo = new UserQnaQuestionLike();
            vo.setUserId(user.getUserId());
            vo.setUserQuestionBoardCode(like.getUserQuestionBoardCode());

            // 좋아요 시 udpate
            UserQnaQuestionBoard result = service.view(like.getUserQuestionBoardCode());
            service.updatelikecount(like.getUserQuestionBoardCode());

            service.addLike(vo);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 7-2. 좋아요 확인하기
    @GetMapping("/userQuestion/like/{code}")
    public int selectLike(@PathVariable(name="code") int code){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof User) {
            User user = (User) principal;

            UserQnaQuestionLike vo = new UserQnaQuestionLike();
            vo.setUserId(user.getUserId());
            vo.setUserQuestionBoardCode(code);

            service.selectLike(vo);
            if(service.selectLike(vo) == null){
                return 0;
            } else {
                return 1;
            }
        } else{
            return 0;
        }
    }

    // 7-3. 좋아요 취소하기
    @DeleteMapping("userQuestion/like/{code}")
    public void deleteLike(@PathVariable(name="code") int code){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof User) {
            User user = (User) principal;

            UserQnaQuestionLike vo = new UserQnaQuestionLike();
            vo.setUserId(user.getUserId());
            vo.setUserQuestionBoardCode(code);
            service.discountlikecount(code);
            service.deleteLike(vo);
        }
    }

    // answer =============================================================================================
    // 1. answer 작성
    @PostMapping("/userQuestion/answer")
    public ResponseEntity createAnswer(UserQnaAnswerBoardDTO dto){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof User){
            User user = (User) principal;

            UserQnaAnswerBoard vo = new UserQnaAnswerBoard();

            vo.setUser(user);
            vo.setUserNickname(user.getUserNickname());
            vo.setUserImg(user.getUserImg());

            vo.setUserQuestionBoardCode(dto.getUserQuestionBoardCode());

            vo.setUserAnswerContent(dto.getUserAnswerContent());
            vo.setAnswerParentCode(dto.getAnswerParentCode());

            return ResponseEntity.ok().body(answerService.create(vo));
        }

        UserQnaQuestionBoard result = service.view(dto.getUserQuestionBoardCode());
        result.setUserQuestionBoardCount(answerService.getTopLevelAnswers(dto.getUserQuestionBoardCode()).size());
        service.update(result);
        return ResponseEntity.badRequest().build();
    }

    // 2. answer 조회
    @GetMapping("/public/userQuestion/answer/{code}")
    public ResponseEntity<List<UserQnaAnswerBoardDTO>> viewAnswers(@PathVariable(name="code") int code){
        List<UserQnaAnswerBoard> topList = answerService.getTopLevelAnswers(code);
        List<UserQnaAnswerBoardDTO> response = AnswerDetailList(topList, code);

        UserQnaQuestionBoard vo = service.view(code);
        vo.setUserQuestionBoardCount(topList.size());
        service.update(vo);

        return ResponseEntity.ok(response);
    }

    // 반복적인 메서드 빼기
    public UserQnaAnswerBoardDTO reanswerDetail(UserQnaAnswerBoard vo){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (vo.getUserAnswerDateUpdate() == null) {
            vo.setUserAnswerDate(vo.getUserAnswerDate());
        } else {
            vo.setUserAnswerDate(vo.getUserAnswerDateUpdate());
        }

        return UserQnaAnswerBoardDTO.builder()
                .userQuestionBoardCode(vo.getUserQuestionBoardCode())
                .userAnswerBoardCode(vo.getUserAnswerBoardCode())
                .userAnswerContent(vo.getUserAnswerContent())

                .userAnswerDate(vo.getUserAnswerDate())
                .user(UserDTO.builder()
                        .userId(vo.getUser().getUserId())
                        .userNickname(vo.getUser().getUserNickname())
                        .userImg(vo.getUser().getUserImg())
                        .build())
                .build();
    }

    // 재귀법을 위해 메서드 분리
    public List<UserQnaAnswerBoardDTO> AnswerDetailList(List<UserQnaAnswerBoard> answers, int code){
        List<UserQnaAnswerBoardDTO> response = new ArrayList<>();
        for(UserQnaAnswerBoard item : answers) {
            List<UserQnaAnswerBoard> reanswers = answerService.getBottomLevelAnswers(item.getUserAnswerBoardCode(), code);
            List<UserQnaAnswerBoardDTO> reanswersDTO = AnswerDetailList(reanswers, code);

            UserQnaAnswerBoardDTO dto = reanswerDetail(item);
            dto.setAnswers(reanswersDTO);

            response.add(dto);
        }
        return response;
    }

    // 3. answer 수정
    @PutMapping("/userQuestion/answer")
    public ResponseEntity<UserQnaAnswerBoard> updateAnswer(UserQnaAnswerBoardDTO dto){

        UserQnaAnswerBoard vo = answerService.viewAnswer(dto.getUserAnswerBoardCode());

        log.info("vo : " + vo);
        log.info("dto : " + dto);
//
        vo.setUserAnswerContent(dto.getUserAnswerContent());

        UserQnaAnswerBoard result = answerService.update(vo);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(result)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 4. answer 삭제
    @DeleteMapping("/userQuestion/answer/{code}")
    public ResponseEntity<UserQnaAnswerBoard> deleteAnswer(@PathVariable(name="code") int parent){

            for (UserQnaAnswerBoard element : answerService.getBottomLevelAnswers(parent)) {
//                log.info("element : " + element);
//                delete(element.getUserAnswerBoardCode());
                answerService.deleteAnswer(element.getUserAnswerBoardCode());
            }

            // 본인삭제
            answerService.deleteAnswer(parent);

        UserQnaQuestionBoard result = service.view(parent);
        result.setUserQuestionBoardCount(answerService.getTopLevelAnswers(parent).size());
        service.update(result);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }



}