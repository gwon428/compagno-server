package com.project.compagnoserver.domain.SitterBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SitterBoardDTO {

    private int sitterBoardCode;

    private String sitterCategory;

    private String sitterLocation;

    private String sitterTitle;

    private String sitterContent;

    private String userId;

    private Date sitterRegiDate;

    private List<MultipartFile> files;

}
