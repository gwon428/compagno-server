package com.project.compagnoserver.domain.QnaA;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="qna_a_board_image")
public class QnaABoardImage {
    @Id @Column(name="qna_a_img_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaAImgCode;

    @Column(name="qna_a_url")
    private String qnaAUrl;

    @ManyToOne
    @JoinColumn(name="qna_a_code")
    @JsonIgnore
    private QnaABoard qnaACode;
}
