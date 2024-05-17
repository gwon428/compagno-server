package com.project.compagnoserver.domain.NoticeBoard;


import com.project.compagnoserver.domain.ProductBoard.ProductBoardComment;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="notice_board")
public class NoticeBoard {
    @Id
    @Column(name = "notice_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeBoardCode;

    @Column(name = "notice_board_title")
    private String noticeBoardTitle;

    @Column(name = "notice_board_content")
    private String noticeBoardContent;

    @Column(name = "notice_board_regi_date")
    private Date noticeBoardRegiDate;

    @Column(name = "notice_board_view_count")
    private int noticeBoardViewCount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "noticeBoard")
    private List<NoticeBoardComment> comments;

    @OneToMany(mappedBy = "noticeBoard")
    private List<NoticeBoardImage> images;
}
