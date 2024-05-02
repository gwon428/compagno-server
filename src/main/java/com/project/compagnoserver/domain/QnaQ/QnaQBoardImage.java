package com.project.compagnoserver.domain.QnaQ;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.jetbrains.annotations.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="qna_q_board_image")
public class QnaQBoardImage  {
    @Id @Column(name="qna_q_img_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaQImgCode;

    @Column(name="qna_q_url")
    private String qnaQUrl;

    @Column(name="qna_q_code")
    private int qnaQCode;


//    @ManyToOne
//    @JoinColumn(name="qna_q_code")
//    @JsonIgnore
//    private QnaQBoard qnaQCode;
}
