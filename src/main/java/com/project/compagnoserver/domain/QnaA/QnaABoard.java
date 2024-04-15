package com.project.compagnoserver.domain.QnaA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="qna_a_board")
public class QnaABoard {
    @Id @Column(name="qna_a_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaACode;

    @Column(name="qna_q_code")
    private int qnaQCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="qna_a_title")
    private String qnaATitle;

    @Column(name="qna_a_content")
    private String qnaAContent;

    @Column(name="qna_a_date")
    private Timestamp qnaADate;

    @OneToMany(mappedBy = "qnaACode")
    @JsonIgnore
    private List<QnaABoardImage> files;
}
