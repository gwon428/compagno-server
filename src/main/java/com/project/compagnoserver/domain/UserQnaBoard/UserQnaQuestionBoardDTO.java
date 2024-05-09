package com.project.compagnoserver.domain.UserQnaBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserQnaQuestionBoardDTO {
    private int userQuestionBoardCode;
    private String userId;
    private String userNickname;
    private String userImg;
    private int animalCategoryCode;

    private String userQuestionBoardTitle;
    private String userQuestionBoardContent;

    private Date userQuestionBoardDate;

    private String userQuestionBoardstatus;
    private int userQuestionBoardCount;

    private ArrayList<MultipartFile> files;
    private List<UserQnaQuestionBoardImage> images;
}
