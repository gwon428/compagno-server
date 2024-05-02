package com.project.compagnoserver.domain.LostBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name="lost_board")
public class LostBoard {

    @Id
    @Column(name="lost_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lostBoardCode;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="user_img")
    private String userImg;

    @Column(name="user_phone")
    private String userPhone;

//    @Column(name="lost_title")
//    private String lostTitle;


    @Column(name="lost_regi_date")
    private Date lostRegiDate;

    @Column(name="lost_view_count")
    private int lostViewCount;

    @Column(name="lost_animal_image")
    private String lostAnimalImage;

    @Column(name="lost_animal_name")
    private String lostAnimalName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="lost_date")
    private Date lostDate;

    @Column(name="lost_location")
    private String lostLocation;

    @Column(name="lost_location_detail")
    private String lostLocationDetail;

    @Column(name="lost_animal_kind")
    private String lostAnimalKind;

    @Column(name="lost_animal_color")
    private String lostAnimalColor;

    @Column(name="lost_animal_gender")
    private String lostAnimalGender;

    @Column(name="lost_animal_age")
    private int lostAnimalAge;

    @Column(name="lost_animal_feature")
    private String lostAnimalFeature;

    @Column(name="lost_animal_RFID")
    private String lostAnimalRFID;

    @OneToMany(mappedBy = "lostBoardCode")
    private List<LostBoardImage> images;

}
