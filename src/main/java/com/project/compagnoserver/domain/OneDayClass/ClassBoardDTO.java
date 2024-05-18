package com.project.compagnoserver.domain.OneDayClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassBoardDTO {

    private int odcCode;
    private String odcTitle;
    private String odcAccompaying;
    private String odcContent;
    private String odcRegiDate;
    private String odcStartDate;
    private String odcLastDate;
    private MultipartFile file; // 새로추가된이미지
    private int imageCode; // 이미지코드를 받는거
    private String imageURL; // 이미지URL을 받는거
    private String userId; // 입력받을 아이디

}