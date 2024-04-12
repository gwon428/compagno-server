package com.project.compagnoserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalBoardDTO {

    private List<MultipartFile> files;
    private String id; // 유저정보
    private String animalMainImage;
    private String animalBoardTitle;
    private String animalBoardContent;
    private String animalBoardView;
    private Date animalBoardDate;

}
