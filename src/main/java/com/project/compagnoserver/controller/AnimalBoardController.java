package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.AnimalBoard;
import com.project.compagnoserver.domain.AnimalBoardDTO;
import com.project.compagnoserver.service.AnimalBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
public class AnimalBoardController {

    @Autowired
    private AnimalBoardService animalBoardService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 자유게시판 글쓰기
    @PostMapping("/animal-board")
    public ResponseEntity<AnimalBoardDTO> write(AnimalBoardDTO dto) throws IOException {
        // 글작성
        AnimalBoard vo = new AnimalBoard(); // 추가로 필요한것 : userId/ animalCateCode
        vo.setAnimalBoardTitle(dto.getAnimalBoardTitle());
        vo.setAnimalBoardContent(dto.getAnimalBoardContent());
        vo.setAnimalBoardDate(dto.getAnimalBoardDate());
        AnimalBoard result = animalBoardService.write(vo);

        // 글에 필요한 사진 넣기- 어떤 글의 사진? => animal_board_code 필요
        for(MultipartFile file : dto.getFiles()){

            String fileName =  file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + "review" + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            file.transferTo(savePath);

//            AnimalBoardImage image = AnimalBoardImage
        }


      animalBoardService.write(vo);
        return null;
    }
}
