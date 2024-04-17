package com.project.compagnoserver.domain.QnaQ;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.QnaA.QnaABoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="qna_q_board")
public class QnaQBoard {
    @Id @Column(name="qna_q_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaQCode;

    @OneToOne
    @JoinColumn(name="qna_a_code")
    private QnaABoard qnaACode;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="qna_q_title")
    private String qnaQTitle;

    @Column(name="qna_q_content")
    private String qnaQContent;

    @CreationTimestamp
    @Column(name="qna_q_date")
    private Timestamp qnaQDate;

    @Column(name="qna_q_status")
    private String qnaQStatus;

    @OneToMany(mappedBy = "qnaQCode")
    @JsonIgnore
    private List<QnaQBoardImage> files;

    @Column
    private String secret;
}
