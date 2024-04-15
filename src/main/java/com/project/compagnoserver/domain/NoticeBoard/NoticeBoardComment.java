package com.project.compagnoserver.domain.NoticeBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="notice_board_comment")
public class NoticeBoardComment {

    @Id
    @Column(name = "notice_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeCommentCode;

    @Column(name = "notice_board_code")
    private int noticeBoardCode;

    @Column(name = "notice_comment_content")
    private String noticeCommentContent;

    @Column(name = "notice_comment_regi_date")
    private Date noticeCommentRegiDate;

    @Column(name = "notice_comment_delete")
    private char noticeCommentDelete;

    @Column(name = "notice_parent_code")
    private int noticeParentCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "notice_parent_code", referencedColumnName = "notice_comment_code", insertable = false, updatable = false)
    private NoticeBoardComment parent;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
