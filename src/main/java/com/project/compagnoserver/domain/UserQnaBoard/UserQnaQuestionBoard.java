package com.project.compagnoserver.domain.UserQnaBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @DynamicInsert @Builder @Table(name="user_question_board")
public class UserQnaQuestionBoard {

    @Id
    @Column(name="user_question_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userQuestionBoardCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="user_img")
    private String userImg;

    @Column(name="animal_category_code")
    private int animalCategoryCode;

    @Column(name="user_question_board_title")
    private String userQuestionBoardTitle;

    @Column(name="user_question_board_content")
    private String userQuestionBoardContent;

    @Column(name="user_question_board_date")
    @CreationTimestamp
    private Timestamp userQuestionBoardDate;

    @Column(name="user_question_board_date_update")
    @UpdateTimestamp
    private Timestamp userQuestionBoardDateUpdate;

    @Column(name="user_question_board_status")
    private char userQuestionBoardStatus;

    @Column(name="user_question_board_count")
    private int userQuestionBoardCount;

    @Column(name="viewcount")
    private int viewcount;
}
