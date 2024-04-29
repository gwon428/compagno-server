package com.project.compagnoserver.domain.NeighborBoard;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="neighbor_board")
public class NeighborBoard {

    @Id
    @Column(name = "neighbor_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int neighborBoardCode;

    @ManyToOne
    @JoinColumn(name = "animal_category_code")
    private AnimalCategory animalCategoryCode;

    @ManyToOne
    @JoinColumn(name = "location_code")
    private LocationParsing locationCode;

    @Column(name = "neighbor_title")
    private String neighborBoardTitle;

    @Column(name = "neighbor_content")
    private String neighborBoardContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "neighbor_view_count")
    private int neighborBoardViewCount;

    @Column(name = "neighbor_regi_date")
    private Date neighborBoardRegiDate;

    @Column(name = "neighbor_update_date")
    private Date neighborBoardUpdateDate;

    @Column(name = "neighbor_status")
    private String neighborBoardStatus;

    @OneToMany(mappedBy = "neighborBoard")
    private List<NeighborBoardImage> images;

}
