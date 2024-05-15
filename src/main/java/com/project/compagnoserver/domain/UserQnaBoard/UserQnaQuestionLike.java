package com.project.compagnoserver.domain.UserQnaBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="user_question_like")
public class UserQnaQuestionLike {
    @Id
    @Column(name="user_question_like_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userQuestionLikeCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_question_board_code")
    private int userQuestionBoardCode;
}
