package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardDTO;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardMainImage;
import com.project.compagnoserver.service.OneDayClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/public/ClassBoard")
    public ResponseEntity<ClassBoard> insert(ClassBoardDTO dto) throws IOException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(dto.getOdcStartDate());
        Date lastDate = sdf.parse(dto.getOdcLastDate());

        String fileName = dto.getFile().getOriginalFilename(); // 파일 업로드
        String uuid = UUID.randomUUID().toString();  // UUID
        String saveName = uploadPath + File.separator + "ClassBoard" + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName);
        dto.getFile().transferTo(savePath); // 파일 업로드 실제로 일어나고 있음!

        ClassBoard vo = ClassBoard.builder()
                .odcTitle(dto.getOdcTitle())
                .odcContent(dto.getOdcContent())
                .odcStartDate(startDate)
                .odcLastDate(lastDate)
                .odcAccompaying('Y')
                .build();

        ClassBoard result = service.insert(vo); // 등록할때 사용자가 직접 적어서 DB로 넣는걸 저장 사키고
        
        ClassBoardMainImage mainImage = new ClassBoardMainImage(); // 객체를 생성해서
        mainImage.setOdcMainImage(saveName); // 그객체에다가 파일을 담겠다

        mainImage.setClassBoard(ClassBoard.builder()
                        .odcCode(result.getOdcCode())
                .build());
        // 그리고 객체에 저장된것중에 테이블 코드를 가져와서 넣고 mainImage에 보내서 담고


        service.create(mainImage); // 마지막으로 저장

        if (result != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 클래스 전체 보기
    @GetMapping("/public/ClassBoard")
    public ResponseEntity <List<ClassBoard>> viewAll(@RequestParam(name = "page", defaultValue = "1") int page){
        Sort sort = Sort.by("odcCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10);
       List<ClassBoard> list = service.viewAll();
       return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    // 클래스 한개 보기
    @GetMapping("/public/ClassBoard/{odcCode}")
    public ResponseEntity view(@PathVariable("odcCode") int odcCode){
        ClassBoard vo = service.view(odcCode);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    // 등록된 클래스 수정
    @PutMapping("/public/ClassBoard")
    public ResponseEntity<ClassBoard> update(ClassBoardDTO dto) throws ParseException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(dto.getOdcStartDate());
        Date lastDate = sdf.parse(dto.getOdcLastDate());

        // 기본에 있던 이미지 정보 가져오기
        List<ClassBoardMainImage> images = service.viewImages(dto.getOdcCode());

        for(ClassBoardMainImage image : images){

            if ((dto.getFile()!=null && !dto.getFile().contains(image.getOdcMainImage())))
            File file = new File(image.getOdcMainImage());
            file.
        }

        ClassBoard vo = ClassBoard.builder()
                .odcTitle(dto.getOdcTitle())
                .odcContent(dto.getOdcContent())
                .odcStartDate(startDate)
                .odcLastDate(lastDate)
                .odcAccompaying('Y')
                .build();

        ClassBoardMainImage prev = service.create(dto.getOdcCode());

        if (dto.getFile().isEmpty()){
            // 새로운 사진이 없는 경우 -> 기존 경로로 vo에 담아낸다.
            vo.setImages(prev.getImages());
        } else {
            // 기존에 있는거 삭제후 새로운거 업로드
            File file = new File(prev.getImages());

            file.delete();

            String fileName = dto.getFile().getOriginalFilename(); // 파일 업로드
            String uuid = UUID.randomUUID().toString();  // UUID
            String saveName = uploadPath + File.separator + "ClassBoard" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            dto.getFile().transferTo(savePath); // 파일 업로드 실제로 일어나고 있음!

            vo.setImages(saveName);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 등록된 클래스 삭제 관련 !!
    @DeleteMapping("/public/ClassBoard/{odcCode}")
    public ResponseEntity delete(@PathVariable("odcCode") int odcCode){
        // 파일 삭제 로직
        ClassBoard prev = service.view(odcCode); // 파일을 삭제하기 위해서는 odcCode를 가져와야한다 !

        File file = new File(prev.setImages(prev.getOdcCode());
        file.delete();

        ClassBoard result = service.delete(odcCode);

        return (result != null) ?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(result) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
