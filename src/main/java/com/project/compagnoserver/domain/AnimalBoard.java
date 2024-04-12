package com.project.compagnoserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animal_board")
public class AnimalBoard {

    @Id
    @Column(name = "animal_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalBoardCode;

    //private int animalCategoryCode; // 카테고리 테이블과 연결

    @Column(name = "animal_main_image")
    private String animalMainImage;

    @Column(name = "animal_board_title")
    private String animalBoardTitle;

    @Column(name = "animal_board_content")
    private String animalBoardContent;

    @Column(name = "animal_board_view")
    private int animalBoardView;

    @Column(name = "animal_board_date")
    private Date animalBoardDate;

    @OneToMany(mappedBy = "animalBoard")
    private List<AnimalBoardImage> images;
    /*
    // 메모 : mappedBy = "animal_board"
    *Collection 'com.project.compagnoserver.domain.AnimalBoard.images' is
    *'mappedBy' a property named 'animal_board'
    *which does not exist in the target entity
    *'com.project.compagnoserver.domain.AnimalBoardImage'
    *여기서 말하는 property name은 양방향성 관계의 주체 Entity에 명시된 animal_board의
    * 자바 형태의 컬럼명; animalBoard
    * */

    //private String userId; // 유저테이블과 연결
}

/*
* CREATE TABLE animal_board(
	a_board_code int PRIMARY KEY AUTO_INCREMENT,
    animal_category_code INT,
    a_main_image varchar(300),
    a_board_title varchar(50),
    a_board_content text,
    a_board_view int DEFAULT 0,
    a_board_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(20)
);
* */
