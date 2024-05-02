package com.project.compagnoserver.domain.NeighborBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name = "neighbor_board_image")
public class NeighborBoardImage {

    @Id
    @Column(name = "neighbor_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int neighborImageCode;

    @ManyToOne
    @JoinColumn(name = "neighbor_board_code")
    @JsonIgnore
    private NeighborBoard neighborBoard;

    @Column(name = "neighbor_bd_image")
    private String neighborImage;

}
