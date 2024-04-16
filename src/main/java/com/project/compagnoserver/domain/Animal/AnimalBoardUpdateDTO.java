package com.project.compagnoserver.domain.Animal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimalBoardUpdateDTO {

//    private List<MultipartFile> files;
    private int animalBoardCode;
    private String id; // 유저정보
    private String animalMainImage;
    private String animalBoardTitle;
    private String animalBoardContent;
    private String animalBoardView;
    private Date animalBoardDate;
    private int animalCategoryCode;

    // 자유게시판 수정DTO
}
