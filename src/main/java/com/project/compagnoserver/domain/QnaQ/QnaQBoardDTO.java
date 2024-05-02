package com.project.compagnoserver.domain.QnaQ;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class QnaQBoardDTO {

    private int qnaQCode;
    private int qnaACode;
    private String userId;
    private String userNickname;
    private String qnaQTitle;
    private String qnaQContent;
    private Timestamp qnaQDate;
    private String qnaQStatus;
    private String secret;

    private List<MultipartFile> files;
    private List<QnaQBoardImage> images;
}
