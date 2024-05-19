package com.project.compagnoserver.domain.AdoptionBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionBoardDTO {

    private int adopBoardCode;
    private String userId;
    private String userImg;
    private String userPhone;
    private String userNickname;

    private String adopRegiDate;
    private int adopViewCount;
    private String adopAnimalImage;
    private String adopAnimalKind;
    private String adopAnimalColor;
    private String adopAnimalFindplace;
    private String adopAnimalGender;
    private String adopAnimalNeuter;
    private int adopAnimalAge;
    private int adopAnimalKg;
    private String adopAnimalFeature;
    private String adopCenterName;
    private String adopCenterPhone;

    private List<MultipartFile> images;
    private List<String> image;

}
