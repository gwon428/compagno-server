package com.project.compagnoserver.domain.UserQnaBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @DynamicInsert @Table(name="user_question_board_image")
public class UserQnaQuestionBoardImage {

    @Id
    @Column(name="user_question_img_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userQuestionImgCode;

    @Column(name="user_question_board_code")
    private int userQuestionBoardCode;

    @Column(name="user_question_img_url")
    private String userQuestionImgUrl;


}
