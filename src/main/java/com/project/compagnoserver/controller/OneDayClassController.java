package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardDTO;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardMainImage;
import com.project.compagnoserver.service.OneDayClassService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class OneDayClassController {

    @Autowired // serviceClass 가져오기
    private OneDayClassService service;

    // 파일 업로드 관련 로직 !
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath; // D:\\upload   // 파일 업로드 관련 위치!

    // 클래스 등록 + 사진 DTO로해서 가져오기
    @PostMapping("/ClassBoard")
    public ResponseEntity<ClassBoard> insert(ClassBoardDTO dto) throws IOException {

        String fileName = dto.getFile().getOriginalFilename(); // 파일 업로드
        String uuid = UUID.randomUUID().toString();  // UUID
        String saveName = uploadPath + File.separator + "ClassBoard" + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName);
        dto.getFile().transferTo(savePath); // 파일 업로드 실제로 일어나고 있음!

        // ClassBoard에다가 vo 값들 담아서 요청!
        ClassBoard vo = new ClassBoard();
        vo.setOdcTitle(dto.getOdcTitile());
        vo.setOdcContent(dto.getOdcContent());

        ClassBoardMainImage mainImage = new ClassBoardMainImage();
        mainImage.setOdcMainImage(saveName);


        ClassBoard result = service.insert(vo);
        if (result != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 클래스 전체 보기
    @GetMapping("/ClassBoard")
    public ResponseEntity <List<ClassBoard>> viewAll(@RequestParam(name = "page", defaultValue = "1") int page){
        Sort sort = Sort.by("odcCode").descending();
        Pageable pageable = PageRequest.of(page-1, 10);
       List<ClassBoard> list = service.viewAll();
       return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    // 클래스 한개 보기
    @GetMapping("/ClassBoard/{odcCode}")
    public ResponseEntity view(@PathVariable("odcCode") int odcCode){
        ClassBoard vo = service.view(odcCode);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    // 등록된 클래스 수정
    @PutMapping("/ClassBoard")
    public ResponseEntity<ClassBoard> update(ClassBoardDTO dto){
        ClassBoardMainImage vo = new ClassBoardMainImage();

        if (dto.getFile().isEmpty()){
            // 새로운 사진이 없는 경우 -> 기존 경로로 vo에 담아낸다.
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 등록된 클래스 삭제
    @DeleteMapping("/ClassBoard/{odcCode}")
    public ResponseEntity delete(@PathVariable("odcCode") int odcCode){
        // 파일 삭제 로직
        ClassBoard prev = service.view(odcCode);
//        File file = new File(prev.getOdc());

        service.delete(odcCode);


        return ResponseEntity.status(HttpStatus.OK).build();

    }


}
