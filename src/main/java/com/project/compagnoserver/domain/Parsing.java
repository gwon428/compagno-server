package com.project.compagnoserver.domain;

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

    @Column(name = "sub_cate")
    private String subCate;

    @Column(name = "mainreg_cate")
    private String mainregCate;

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
    private Date holyday;

    @Column
    private String parking;

    @Column
    private String fee;
}
