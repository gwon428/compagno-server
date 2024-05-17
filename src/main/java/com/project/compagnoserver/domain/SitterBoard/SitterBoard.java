package com.project.compagnoserver.domain.SitterBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.Animal.AnimalCategory;
import com.project.compagnoserver.domain.Parsing.LocationParsing;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="sitter_board")
public class SitterBoard {

    @Id
    @Column(name = "sitter_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sitterBoardCode;

    @ManyToOne
    @JoinColumn(name = "sitter_category_code")
    private SitterCategory sitterCategory;

    @ManyToOne
    @JoinColumn(name = "animal_category_code")
    private AnimalCategory animalCategoryCode;
//(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "location_code")
    private LocationParsing location;

    @Column(name = "sitter_title")
    private String sitterTitle;

    @Column(name = "sitter_content")
    private String sitterContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sitter_view_count")
    private int sitterViewCount;

    @Column(name = "sitter_regi_date")
    private Date sitterRegiDate;

    @Column(name = "sitter_update_date")
    private Date sitterUpdateDate;

    @OneToMany(mappedBy = "sitterBoard")
    private List<SitterBoardImage> images;

}
