package com.project.compagnoserver.domain.QnaA;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class QnaABoardDTO {
    private int qnaACode;
    private int qnaQCode;
    private String userId;
    private String userNickname;
    private String qnaATitle;
    private String qnaAContent;

    private Date qnaADate;

    private List<MultipartFile> files;
    private List<QnaABoardImage> images;

}
