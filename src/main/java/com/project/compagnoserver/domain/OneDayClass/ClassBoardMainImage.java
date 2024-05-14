package com.project.compagnoserver.domain.OneDayClass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "oneday_class_board_main_image")
public class ClassBoardMainImage {

    @Id
    @Column(name = "odc_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odcImageCode;
    // 원데이 클래스 메인 이미지 코드

    @ManyToOne
    @JoinColumn(name = "odc_code")
    @JsonIgnore
    private ClassBoard classBoard;
    // ClassBoard랑 조인 !!

    @Column(name = "odc_main_image")
    private String odcMainImage;
    // 원데이클래스 메인이미지 URL
}

