package com.project.compagnoserver.domain.QnaQ;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="qna_q_board_image")
public class QnaQBoardImage {
    @Id @Column(name="qna_q_board_img_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaQBoardImgCode;

    @Column(name="qna_q_board_url")
    private String qnaQBoardUrl;

    @ManyToOne
    @JoinColumn(name="qna_q_board_code")
    @JsonIgnore
    private QnaQBoard qnaQBoardCode;
}
