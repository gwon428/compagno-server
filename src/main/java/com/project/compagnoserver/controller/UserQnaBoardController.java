package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.UserQnaBoard.*;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.UserQnaAnswerBoardService;
import com.project.compagnoserver.service.UserQnaQuestionBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // Question =============================================================================================
    // 1. 질문 등록
    @PostMapping("/userQuestion")
    public ResponseEntity<UserQnaQuestionBoard> create(UserQnaQuestionBoardDTO dto) throws IOException {
        log.info("dto : " + dto);

        // dto로 받은 값을 builder를 통해 vo 형태로 변환
        UserQnaQuestionBoard vo = new UserQnaQuestionBoard();

        vo.setUserId(dto.getUserId());
        vo.setUserNickname(dto.getUserNickname());
        vo.setUserImg(dto.getUserImg());

        vo.setUserQuestionBoardTitle(dto.getUserQuestionBoardTitle());
        vo.setUserQuestionBoardContent(dto.getUserQuestionBoardContent());


        UserQnaQuestionBoard result = service.create(UserQnaQuestionBoard.builder()
                        .userId(dto.getUserId())
                        .userNickname(dto.getUserNickname())
                        .userImg(dto.getUserImg())

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
                    img.setUserQuestionImgUrl(saveName.substring(27));
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
                                                              @RequestParam(name="page", defaultValue = "1") int page){
        Sort sort = Sort.by("userQuestionBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        QUserQnaQuestionBoard qUserQnaQuestionBoard = QUserQnaQuestionBoard.userQnaQuestionBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression;

        if(title != null){
            expression = qUserQnaQuestionBoard.userQuestionBoardTitle.like("%" + title + "%");
            builder.and(expression);
        }
        if(id != null){
            expression = qUserQnaQuestionBoard.userId.like("%" + id + "%");
            builder.and(expression);
        }
        if(content != null){
            expression = qUserQnaQuestionBoard.userQuestionBoardContent.like("%" + content + "%");
            builder.and(expression);
        }
        if(category != null){
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

        Page<UserQnaQuestionBoard> list = service.viewAll(builder, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
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
                .animalCategoryCode(result.getAnimalCategoryCode())
                .userId(result.getUserId())
                .userNickname(result.getUserNickname())
                .userImg(result.getUserImg())
                .images(service.viewImg(code))
                .build();

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

                    img.setUserQuestionImgUrl(saveName.substring(27));
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
                        .userQuestionBoardStatus("N")
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
        return null;
    }

    // 6-2. 답변 취소하기
    @DeleteMapping("/userQuestion/answerChoose/{code}")
    public void deleteChoose(@PathVariable(name="code") int code){
        UserQnaAnswerChoose chooseAnswer = service.getChoose(code);
        log.info("취소할 답변 : " + chooseAnswer);
        service.deleteChoose(chooseAnswer.getChooseCode());
    }

    // 6-3. 채택된 답변 보기!
    @GetMapping("/public/userQuestion/answerChoose/{code}")
    public ResponseEntity<UserQnaAnswerBoard> getAnswer(@PathVariable(name="code") int code){
        // questionBoardCode로 choose 찾아서 그와 연결된 answer 출력
        UserQnaAnswerChoose choose = service.getChoose(code);
        UserQnaAnswerBoard chooseAnswer;
        if(choose != null){
             chooseAnswer = answerService.viewAnswer(choose.getUserAnswerBoardCode());
        } else {
             chooseAnswer= null;
        }

        return chooseAnswer != null ? ResponseEntity.status(HttpStatus.OK).body(chooseAnswer) : ResponseEntity.status(HttpStatus.OK).build();
    }

    // answer =============================================================================================
    // 1. answer 작성
    @PostMapping("/userQuestion/answer")
    public ResponseEntity createAnswer(UserQnaAnswerBoardDTO dto){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
    log.info("dto : " + dto);
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
        return ResponseEntity.badRequest().build();
    }

    // 2. answer 조회
    @GetMapping("/public/userQuestion/answer/{code}")
    public ResponseEntity<List<UserQnaAnswerBoardDTO>> viewAnswers(@PathVariable(name="code") int code){
        List<UserQnaAnswerBoard> topList = answerService.getTopLevelAnswers(code);
        List<UserQnaAnswerBoardDTO> response = AnswerDetailList(topList, code);

        return ResponseEntity.ok(response);
    }

    // 반복적인 메서드 빼기
    public UserQnaAnswerBoardDTO reanswerDetail(UserQnaAnswerBoard vo){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
        if(dto.getUserAnswerDate() == null){
            vo.setUserAnswerDate(dto.getUserAnswerDateUpdate());
        } else {
            vo.setUserAnswerDateUpdate(dto.getUserAnswerDate());
        }
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }



}