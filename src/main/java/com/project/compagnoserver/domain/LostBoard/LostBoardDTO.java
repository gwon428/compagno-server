package com.project.compagnoserver.domain.LostBoard;

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
public class LostBoardDTO {

    private int lostBoardCode;
    private String userId;
    private String userImg;
    private String userPhone;
    private String lostTitle;
    private String lostAnimalImage;
    private String lostAnimalName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lostDate;
    private String lostLocation;
    private String lostAnimalKind;
    private String lostAnimalColor;
    private String lostAnimalGender;
    private int lostAnimalAge;
    private String lostAnimalFeature;
    private int lostAnimalRFID;


    private List<MultipartFile> images;
}
