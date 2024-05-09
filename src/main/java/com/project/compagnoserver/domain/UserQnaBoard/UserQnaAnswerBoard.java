package com.project.compagnoserver.domain.UserQnaBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @DynamicInsert @Builder
@Table(name="user_answer_board")
public class UserQnaAnswerBoard {

    @Id @Column(name="user_answer_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userAnswerBoardCode;

    @Column(name="user_question_board_code")
    private int userQuestionBoardCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="user_img")
    private String userImg;

    @Column(name="user_answer_content")
    private String userAnswerContent;

    @Column(name="user_answer_date")
    private String userAnswerDateUpdate;

}
