package domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @Column(name = "notice_board_regi-date")
    private Date noticeBoardRegiDate;

    @Column(name = "notice_board_hits")
    private int noticeBoardHits;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "notice_board")
    private List<NoticeBoardImage> images;
}
