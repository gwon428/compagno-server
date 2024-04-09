package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.AnimalBoard;
import com.project.compagnoserver.domain.AnimalBoardDTO;
import com.project.compagnoserver.service.AnimalBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
public class AnimalBoardController {

    @Autowired
    private AnimalBoardService animalBoardService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath; // 업로드 경로 역할을 해줌(D:\\upload)

    @PostMapping("/animal-board")
    public ResponseEntity<AnimalBoard> write(AnimalBoardDTO dto){

        // 일단 글에 써져야 함
        AnimalBoard vo = new AnimalBoard();
        return null;
    }
}
