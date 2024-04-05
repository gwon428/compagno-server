package com.project.compagnoserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalBoardDTO {

    private int aBoardCode;
    private String aBoardTitle;
    private String aBoardContent;
    private int aBoardView;
    private Date aBoardDate;
    private MultipartFile file; // 여기에 사진이 담김
    private int animalCategoryCode; // 얘를 통해서 타입을 가져옴
}
