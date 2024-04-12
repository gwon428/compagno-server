package com.project.compagnoserver.domain.LostBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="lost_board_comment")
public class LostBoardComment {

    @Id
    @Column(name="lost_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lostCommentCode;

    @ManyToOne
    @JoinColumn(name="user_id")
    //private String userId;
    private User user; //-> 커밋 후 변경

    @Column(name="user_img")
    private String userImg;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="comment_content")
    private String commentContent;

    @Column(name="comment_date")
    private Timestamp commentDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="lost_parent_code", referencedColumnName = "lost_comment_code", insertable = false, updatable = false)
    private LostBoardComment lostParentCode;

    @Column(name="lost_board_code")
    private int lostBoardCode;

}
