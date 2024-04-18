package com.project.compagnoserver.domain.SitterBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "sitter_board_comment")
public class SitterBoardComment {

    @Id
    @Column(name = "sitter_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sitterCommentCode;

    @Column(name = "sitter_board_code")
    private int sitterBoardCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sitter_com_content")
    private String sitterCommentContent;

    @Column(name = "sitter_com_regi_date")
    private Date sitterCommentRegiDate;

    @Column(name = "sitter_com_status")
    private String sitterCommentStatus;

    @Column(name = "sitter_com_delete_date")
    private Date sitterCommentDelDate;

    @Column(name = "sitter_com_parent_code")
    private int sitterCommentParentCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sitter_com_parent_code", referencedColumnName = "sitter_comment_code", insertable = false, updatable = false)
    private SitterBoardComment parent;

}
