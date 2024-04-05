package com.project.compagnoserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "animal_board")
public class AnimalBoard {

    @Id
    @Column(name = "a_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aBoardCode;  //자유게시판 코드

    @ManyToOne
    @Column(name = "animal_category_code")
    private AnimalCategory animalCategory; // 동물 카테고리 코드로 카테고리 가져오기

    @Column(name = "a_main_image")
    private String aMainImage; // 게시글 썸네일

    @Column(name = "a_board_title")
    private String aBoardTitle; // 게시글 제목

    @Column(name = "a_board_content")
    private String aBoardContent; // 게시글 내용

    @Column(name = "a_board_view")
    private int aBoardView; //게시글 조회수

    @Column(name = "a_board_date")
    private Date aBoardDate; // 게시글 작성일자

//    @ManyToOne
//    @JoinColumn(name ="id")
//    private User user; // 유저테이블의 아이디와 foreign key

}
