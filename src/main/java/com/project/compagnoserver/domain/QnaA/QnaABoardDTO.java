package com.project.compagnoserver.domain.QnaA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class QnaABoardDTO {
    private List<MultipartFile> files;
    private int qnaACode;
    private int qnaQCode;
    private String userId;
    private String userNickname;
    private String qnaATitle;
    private String qnaAContent;
}
