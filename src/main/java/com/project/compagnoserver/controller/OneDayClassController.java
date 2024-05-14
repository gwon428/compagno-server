package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardDTO;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardMainImage;
import com.project.compagnoserver.domain.OneDayClass.QClassBoard;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.OneDayClassService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class OneDayClassController {

    @Autowired // serviceClass 가져오기
    private OneDayClassService service;

    // 파일 업로드 관련 로직 !
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath; // D:\\upload   // 파일 업로드 관련 위치!

    // 클래스 등록 + 사진 DTO로해서 가져오기
    @PostMapping("/ClassBoard")
    public ResponseEntity<ClassBoard> insert(ClassBoardDTO dto) throws IOException, ParseException {

        // 날짜 관련 로직 !!===============================================
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(dto.getOdcStartDate());
        Date lastDate = sdf.parse(dto.getOdcLastDate());
        // log.info("dto : " + dto);

        // 사용자에게 입력받을거 !!
        ClassBoard vo = ClassBoard.builder()
                .user(userInfo())
                .odcTitle(dto.getOdcTitle())
                .odcContent(dto.getOdcContent())
                .odcStartDate(startDate)
                .odcLastDate(lastDate)
                .odcAccompaying(dto.getOdcAccompaying().charAt(0))
                .user(User.builder()
                        .userId(userInfo().getUserId()).build())
                .build();

        ClassBoard result = service.insert(vo); // 등록할때 사용자가 적은걸 저장시키겠다 !

        if (dto.getFile() != null) { // dto에 있는 파일이 존재한다면 !
            // log.info("file : " + dto.getFile());
            ClassBoardMainImage imgVo = new ClassBoardMainImage(); // 객체 생성해서 받아내겠다.
            // 파일 업로드 관련 ==================================================
            String fileName = dto.getFile().getOriginalFilename(); // 파일 업로드
            String uuid = UUID.randomUUID().toString();  // UUID
            String saveName = uploadPath + File.separator + "ClassBoard" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            dto.getFile().transferTo(savePath); // 파일 업로드 실제로 일어나고 있음!

            imgVo.setOdcMainImage(saveName);
            imgVo.setClassBoard(result);

            service.createImg(imgVo);

        }


        if (result != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 클래스 전체 보기
    @GetMapping("/public/ClassBoard")
    public ResponseEntity<List<ClassBoard>> viewAll() {
        List<ClassBoard> list = service.viewAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    // 클래스 한개 보기
    @GetMapping("/public/ClassBoard/{odcCode}")
    public ResponseEntity view(@PathVariable(name = "odcCode") int odcCode) {
        ClassBoard vo = service.view(odcCode);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    // 등록된 클래스 수정
    @PutMapping("/ClassBoard")
    public ResponseEntity update(ClassBoardDTO dto) throws ParseException, IOException {

        // file이 null이 아닐 때만 기존 이미지 삭제하고 해당 이미지 추가!
        if(dto.getFile()!=null) {
            // 기존 이미지 삭제 로직! 해당 경로에서 삭제하는 거랑 DB에서 해당 정보 삭제  (imageCode, imageURL)
            // 반복문을 돌려서 url로 객체에 delete사용
            File file = new File(dto.getImageURL());
            file.delete();

            // 동시에 이미지 테이블 관련 이미지의 odcCode로 삭제 기능 진행
            service.deleteImg(dto.getImageCode());

            // 새로운 이미지 추가하는 로직! (file)
            ClassBoardMainImage imgVo = new ClassBoardMainImage(); // 객체 생성해서 받아내겠다.
            // 파일 업로드 관련 ==================================================
            String fileName = dto.getFile().getOriginalFilename(); // 파일 업로드
            String uuid = UUID.randomUUID().toString();  // UUID
            String saveName = uploadPath + File.separator + "ClassBoard" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            dto.getFile().transferTo(savePath); // 파일 업로드 실제로 일어나고 있음!

            imgVo.setOdcMainImage(saveName);
            imgVo.setClassBoard(ClassBoard.builder().odcCode(dto.getOdcCode()).build());

            service.createImg(imgVo);

        }

        //////
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(dto.getOdcStartDate());
        Date lastDate = sdf.parse(dto.getOdcLastDate());

        // 원데이 클래스 수정할것들
        ClassBoard vo = ClassBoard.builder()
                .odcCode(dto.getOdcCode())
                .user(User.builder()
                        .userId(userInfo().getUserId()).build())
                .odcTitle(dto.getOdcTitle())
                .odcContent(dto.getOdcContent())
                .odcStartDate(startDate)
                .odcLastDate(lastDate)
                .odcAccompaying(dto.getOdcAccompaying().charAt(0))
                .build();

        service.insert(vo);


        return ResponseEntity.ok().build();


    }

    // 등록된 클래스 삭제 관련 !!
    @DeleteMapping("/ClassBoard/{odcCode}")
    public ResponseEntity delete(@PathVariable(name = "odcCode") int odcCode) {
        // 파일 삭제 로직
        ClassBoard prev = service.view(odcCode); // 파일을 삭제하기 위해서는 odcCode를 가져와야한다 !

        // 코드는 : odcCode
        // 삭제를 하기위해서는 이미지를 삭제하면서 리뷰이미지도 같이 삭제 !!
        // 1 . 이미지 테이블(onedayClassMainImage)에 해당 odcCode에 대한 이미지들을 가지고 와야한다 List<ClassBoardMainImage>
        //     ==>

        ClassBoardMainImage image = service.viewImg(odcCode); // images변수에다가 정보를 저장

        // 반복문을 돌려서 url로 객체에 delete사용
        File file = new File(image.getOdcMainImage());
        file.delete();

        // 동시에 이미지 테이블 관련 이미지의 odcCode로 삭제 기능 진행
        service.deleteImg(image.getOdcImageCode());

        service.delete(odcCode);

        return ResponseEntity.ok().build();
    }

    // 로그인 정보 가져오기
    public User userInfo() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            User user = (User) principal;
            return user;
        }
        return null;
    }
}
