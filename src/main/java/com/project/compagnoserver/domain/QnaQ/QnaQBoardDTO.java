package com.project.compagnoserver.domain.QnaQ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class QnaQBoardDTO {
    private List<MultipartFile> files;
    private int qnaQCode;
    private String userId;
    private String userNickname;
    private String qnaQTitle;
    private String qnaQContent;
    private String secret;
}
