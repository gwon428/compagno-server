package com.project.compagnoserver.domain.WalkiesBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name = "walkies_board_comment")
public class WalkiesBoardComment {

    @Id
    @Column(name = "walkies_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int walkiesCommentCode;

    @Column(name = "walkies_board_code")
    private int walkiesBoardCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "walkies_com_content")
    private String walkiesCommentContent;

    @Column(name = "walkies_com_regi_date")
    private Date walkiesCommentRegiDate;

    @Column(name = "walkies_com_status")
    private Character walkiesCommentStatus;

    @Column(name = "walkies_com_delete_date")
    private Date walkiesCommentDelDate;

    @Column(name = "walkies_com_parent_code")
    private int walkiesCommentParentCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "walkies_com_parent_code", referencedColumnName = "walkies_comment_code", insertable = false, updatable = false)
    private WalkiesBoardComment walkiesParent;

}
