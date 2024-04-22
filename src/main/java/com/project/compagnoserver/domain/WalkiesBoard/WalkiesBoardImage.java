package com.project.compagnoserver.domain.WalkiesBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name = "walkies_board_image")
public class WalkiesBoardImage {

    @Id
    @Column(name = "walkies_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int walkiesImageCode;

    @ManyToOne
    @JoinColumn(name = "walkies_board_code")
    @JsonIgnore
    private WalkiesBoard walkiesBoard;

    @Column(name = "walkies_bd_image")
    private String walkiesBoardImage;

}
