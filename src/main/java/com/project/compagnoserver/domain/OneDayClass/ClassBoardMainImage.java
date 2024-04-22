package com.project.compagnoserver.domain.OneDayClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
public class ClassBoardMainImage {

    @Id
    @Column(name = "odc_image_code")
    private int odcImageCode;
    // 원데이 클래스 메인 이미지 코드

    @Column(name = "odc_code")
    private int odcCode;
    // 원데이클래스 등록 후 코드

    @Column(name = "odc_main_image")
    private String odcMainImage;
    // 원데이클래스 메인이미지

    @JoinColumn
    private int userId;
    // 유저 아이디
}
