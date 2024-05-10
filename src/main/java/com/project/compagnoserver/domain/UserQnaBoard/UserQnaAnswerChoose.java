package com.project.compagnoserver.domain.UserQnaBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @DynamicInsert @Table(name="user_answer_choose")
public class UserQnaAnswerChoose {
    @Id
    @Column(name="choose_code")
    private int chooseCode;

    @Column(name="user_question_board_code")
    private int userQuestionBoardCode;

    @Column(name="user_answer_board_code")
    private int userAnswerBoardCode;

}
