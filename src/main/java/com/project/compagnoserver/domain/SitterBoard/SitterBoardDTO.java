package com.project.compagnoserver.domain.SitterBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SitterBoardDTO {

    private int sitterBoardCode;

    private String sitterCategory;

    private String sitterLocation;

    private String sitterTitle;

    private String sitterContent;

    private String userId;

    private List<MultipartFile> sitterImages;

}
