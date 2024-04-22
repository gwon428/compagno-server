package com.project.compagnoserver.domain.WalkiesBoard;

import com.project.compagnoserver.domain.Animal.AnimalCategory;
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
@Table(name="walkies_board")
public class WalkiesBoard {

    @Id
    @Column(name = "walkies_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int walkiesBoardCode;

    @ManyToOne
    @JoinColumn(name = "animal_category_code")
    private AnimalCategory animalCategoryCode;

    @Column(name = "walkies_location")
    private String walkiesLocation;

    @Column(name = "walkies_title")
    private String walkiesBoardTitle;

    @Column(name = "walkies_content")
    private String walkiesBoardContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "walkies_view_count")
    private int walkiesBoardViewCount;

    @Column(name = "walkies_regi_date")
    private Date walkiesBoardRegiDate;

    @Column(name = "walkies_update_date")
    private Date walkiesBoardUpdateDate;

    @Column(name = "walkies_status")
    private String walkiesBoardStatus;

    @OneToMany(mappedBy = "walkiesBoard")
    private List<WalkiesBoardImage> images;

}
