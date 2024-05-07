package com.project.compagnoserver.domain.Parsing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
public class Parsing {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column
    private String name;

    @Column(name = "main_cate")
    private String mainCate;

    @Column(name="main_cate_code")
    private int mainCateCode;

    @Column(name = "sub_cate")
    private String subCate;

    @Column(name="sub_cate_code")
    private int subCateCode;

    @Column(name = "mainreg_cate")
    private String mainregCate;

    @Column(name="mainreg_code")
    private int mainregCode;

    @Column(name = "subreg_cate")
    private String subregCate;

    @Column
    private String latitude;

    @Column
    private String longtitude;

    @Column(name = "road_addr")
    private String roadAddr;

    @Column
    private String addr;

    @Column
    private String phone;

    @Column
    private String url;

    @Column
    private String parking;

    @Column
    private String fee;

    // 휴무, 운영시간 추가
    @Column
    private String holiday;

    @Column(name="operating_hours")
    private String OperatingHours;
}
