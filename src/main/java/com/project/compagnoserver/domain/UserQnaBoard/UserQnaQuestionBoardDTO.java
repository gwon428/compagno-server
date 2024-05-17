package com.project.compagnoserver.domain.UserQnaBoard;

import com.project.compagnoserver.domain.user.UserDTO;
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
    private UserDTO user;
    private String userId;
    private String userNickname;
    private String userImg;
    private int animalCategoryCode;

    private String userQuestionBoardTitle;
    private String userQuestionBoardContent;

    private Date userQuestionBoardDate;

    private char userQuestionBoardstatus;
    private int userQuestionBoardCount;
    private int viewcount;

    private ArrayList<MultipartFile> files;
    private List<UserQnaQuestionBoardImage> images;
}
