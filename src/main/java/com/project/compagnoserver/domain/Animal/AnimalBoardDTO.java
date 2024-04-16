package com.project.compagnoserver.domain.Animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalBoardDTO {

//    private List<MultipartFile> files;
    private String id; // 유저정보
    private String animalMainImage;
    private String animalBoardTitle;
    private String animalBoardContent;
    private String animalBoardView;
    private Date animalBoardDate;
    private int animalBoardCode;
    private int animalCategoryCode;

    // 자유게시판 글쓰기 DTO
}
