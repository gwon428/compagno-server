package com.project.compagnoserver.domain.AdoptionBoard;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionBoardDTO {

    private int adopBoardCode;
    private String userId;
    private String userImg;
    private String userNickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date adopRegiDate;
    private int adopViewCount;
    private String adopAnimalImage;
    private String adopAnimalKind;
    private String adopAnimalColor;
    // 입양 동물 발견 장소
    private String adopAnimalFindplace;
    private String adopAnimalGender;
    // 입양 동물 중성화 유무
    private String adopAnimalNeuter;
    private int adopAnimalAge;
    private int adopAnimalKg;
    private String adopAnimalFeature;
    // 보호센터 이름
    private String adopCenterName;
    // 보호 센터 연락처
    private String adopCenterPhone;

    private List<MultipartFile> images;
    private List<String> image;

}
