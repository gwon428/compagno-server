package com.project.compagnoserver.domain.AdoptionBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@Table(name="adoption_board")
public class AdoptionBoard {

    @Id
    @Column(name="adoption_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adopBoardCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="userImg")
    private String userImg;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="user_phone")
    private String userPhone;

    @Column(name="adoption_regi_date")
    private Date adopRegiDate;

    @Column(name="adoption_view_count")
    private int adopViewCount;

    // 입양 동물 게시판 메인 사진
    @Column(name="adoption_animal_image")
    private String adopAnimalImage;

    @Column(name="adoption_animal_kind")
    private String adopAnimalKind;

    @Column(name="adoption_animal_color")
    private String adopAnimalColor;

    // 입양 동물 발견 장소
    @Column(name=" adoption_animal_findplace")
    private String adopAnimalFindplace;

    @Column(name="adoption_animal_gender")
    private String adopAnimalGender;

    // 입양 동물 중성화 유무
    @Column(name="adoption_animal_neuter")
    private String adopAnimalNeuter;

    @Column(name="adoption_animal_age")
    private int adopAnimalAge;

    @Column(name="adoption_animal_kg")
    private int adopAnimalKg;

    @Column(name="adoption_animal_feature")
    private String adopAnimalFeature;

    // 보호센터 이름
    @Column(name="adoption_center_name")
    private String adopCenterName;

    // 보호 센터 연락처
    @Column(name="adoption_center_phone")
    private String adopCenterPhone;

//    @OneToMany(mappedBy = "adopBoardCode", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "adopBoardCode")
    private List<AdoptionBoardImage> images;

}
