package com.project.compagnoserver.domain.UserQnaBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @DynamicInsert @Builder
@Table(name="user_answer_board")
public class UserQnaAnswerBoard {

    @Id @Column(name="user_answer_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userAnswerBoardCode;

    @Column(name="user_question_board_code")
    private int userQuestionBoardCode;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="user_img")
    private String userImg;

    @Column(name="user_answer_content")
    private String userAnswerContent;

    @Column(name="user_answer_date")
    @CreationTimestamp
    private Timestamp userAnswerDate;

    @Column(name="user_answer_date_update")
    @UpdateTimestamp
    private Timestamp userAnswerDateUpdate;

    @Column(name="answer_parent_code")
    private int answerParentCode;

    // 관계만 정의..
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="answer_parent_code", referencedColumnName = "user_answer_board_code", insertable = false, updatable = false)
    private UserQnaAnswerBoard parent;


}
