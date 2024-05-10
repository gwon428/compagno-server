package com.project.compagnoserver.domain.OneDayClass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@Builder
@Table(name="oneday_class_board")
public class ClassBoard {

    // 원데이 클래스 등록시 코드
    @Id
    @Column(name = "odc_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odcCode;

    // 원데이 클래스 제목
    @Column(name = "odc_title")
    private String odcTitle;

    // 원데이 클래스 내용
    @Column(name = "odc_content")
    private String odcContent ;

    // 원데이 클래스 애완동물 동반가능 여부
    @Column(name = "odc_accompaying")
    private char odcAccompaying;

    // 원데이 클래스 등록 날짜
    @Column(name = "odc_regi_date")
    private Date odcRegiDate;

    // 원데이 클래스 시작 날짜
    @Column(name = "odc_start_date")
    private Date odcStartDate;

    // 원데이 클래스 마지막 날짜
    @Column(name = "odc_last_date")
    private Date odcLastDate;

    // 사용자 ID
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

//    =====================================

    @OneToMany(mappedBy ="classBoard")
    private List<ClassBoardMainImage> images;

}
