package com.project.compagnoserver.domain.AdoptionBoard;

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
@Table(name="adoption_board_comment")
public class AdoptionBoardComment {

    @Id
    @Column(name="adoption_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adopCommentCode;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="user_img")
    private String userImg;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="comment_content")
    private String commentContent;

    @Column(name="comment_date")
    private Timestamp commentDate;

    @Column(name="adoption_parent_code")
    private int adopParentCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="adoption_parent_code", referencedColumnName = "adoption_comment_code", insertable = false, updatable = false)
    private AdoptionBoardComment parent;

    @Column(name="adoption_board_code")
    private int adopBoardCode;

}
