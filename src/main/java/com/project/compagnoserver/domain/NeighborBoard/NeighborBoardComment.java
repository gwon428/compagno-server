package com.project.compagnoserver.domain.NeighborBoard;

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
@Table(name = "neighbor_board_comment")
public class NeighborBoardComment {

    @Id
    @Column(name = "neighbor_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int neighborCommentCode;

    @Column(name = "neighbor_board_code")
    private int neighborBoardCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "neighbor_com_content")
    private String neighborCommentContent;

    @Column(name = "neighbor_com_regi_date")
    private Date neighborCommentRegiDate;

    @Column(name = "neighbor_com_status")
    private Character neighborCommentStatus;

    @Column(name = "neighbor_com_delete_date")
    private Date neighborCommentDelDate;

    @Column(name = "neighbor_com_parent_code")
    private int neighborCommentParentCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "neighbor_com_parent_code", referencedColumnName = "neighbor_comment_code", insertable = false, updatable = false)
    private NeighborBoardComment neighborParent;

}

