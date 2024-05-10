package com.project.compagnoserver.domain.UserQnaBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @DynamicInsert @Table(name="user_question_bookmark")
public class UserQnaQuestionBoardBookmark {

    @Id
    @Column(name="user_question_bookmark_code")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userQuestionBookmarkCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_question_board_code")
    private int userQnaQuestionBoard;

}
