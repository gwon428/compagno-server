package com.project.compagnoserver.domain.Animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;


import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "animal_board")
public class AnimalBoard {

    @Id
    @Column(name = "animal_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalBoardCode;

    //.TransientPropertyValueException 오류시 자식객체를 호출하는 부모객체쪽에 cascade = CascadeType.ALL추가
    @ManyToOne
    @JoinColumn(name = "animal_category_code")
    private AnimalCategory animalCategory; // 카테고리 테이블과 연결

//    @Column(name = "animal_category_code")
//    private int animalCategoryCode;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저테이블과 연결
    /*
    // 메모 : mappedBy = "animal_board"
    *Collection 'com.project.compagnoserver.domain.Animal.AnimalBoard.images' is
    *'mappedBy' a property named 'animal_board'
    *which does not exist in the target entity
    *'com.project.compagnoserver.domain.Animal.AnimalBoardImage'
    *여기서 말하는 property name은 양방향성 관계의 주체 Entity에 명시된 animal_board의
    * 자바 형태의 컬럼명; animalBoard
    * */


}
