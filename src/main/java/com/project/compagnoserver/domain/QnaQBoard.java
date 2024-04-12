package com.project.compagnoserver.domain;

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
@Table(name="qna_q_board")
public class QnaQBoard {
    @Id @Column(name="qna_q_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaQBoardCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="qna_q_title")
    private String qnaQTitle;

    @Column(name="qna_q_content")
    private String qnaQContent;

    @Column(name="qna_q_date")
    private Date qnaQDate;

    @Column(name="qna_q_status")
    private String qnaQStatus;

    @OneToMany(mappedBy = "qnaQBoardCode")
    private List<QnaQBoardImage> files;
}
