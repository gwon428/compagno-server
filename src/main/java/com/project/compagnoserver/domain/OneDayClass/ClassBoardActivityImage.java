package com.project.compagnoserver.domain.OneDayClass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert

public class ClassBoardActivityImage {

    @Id
    @Column(name = "odc_review_code")
    @GeneratedValue
    private int odcReviewCode;
    // 원데이 클래스 유저 리뷰 코드

    @Column(name = "odc_code")
    private int odcCode;
    // 원데이 클래스 등록 후 코드

    @Column(name = "odc_active_image")
    private String odcActiveImage;
    // 원데이 클래스 활동관련 이미지

    @JoinColumn
    private int userId;
    // 유저 아이디
}
