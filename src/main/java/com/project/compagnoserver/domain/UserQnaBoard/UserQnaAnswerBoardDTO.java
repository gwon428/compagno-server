package com.project.compagnoserver.domain.UserQnaBoard;

import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserQnaAnswerBoardDTO {
    private int userAnswerBoardCode;
    private int userQuestionBoardCode;

    private UserDTO user;
    private String userNickname;
    private String userImg;

    private String userAnswerContent;
    private Timestamp userAnswerDate;
    private Timestamp userAnswerDateUpdate;

    private int answerParentCode;
    private List<UserQnaAnswerBoardDTO> answers = new ArrayList<>();

}
