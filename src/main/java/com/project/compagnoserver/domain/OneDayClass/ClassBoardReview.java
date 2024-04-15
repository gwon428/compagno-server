package com.project.compagnoserver.domain.OneDayClass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert

public class ClassBoardReview {

    @Id
    @Column(name = "odc_review_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odcReviewCode;
    // 원데이 클래스 유저 리뷰 코드

    @Column(name = "odc_code")
    private int odcCode;
    // 원데이 클래스 등록 후 코드
    
    @Column(name = "odc_review_title")
    private String odcReviewTitle;
    // 원데이 클래스 리뷰 제목
    
    @Column(name = "odc_review_content")
    private String odcReviewContent;
    // 원데이 클래스 리뷰 내용
    
    @Column(name = "odc_review_date")
    private Timestamp odcReviewDate;
    // 원데이 클래스 리뷰 등록 날짜
    
    @Column(name = "odc_review_count")
    private int odcReviewCount;
    // 원데이 클래스 별점

    @JoinColumn
    private String userId;
    // 원데이 클래스 유저 아이디
}
