package com.project.compagnoserver.domain.SitterBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.ProductBoard.ProductBoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name="sitter_board_image")
public class SitterBoardImage {

    @Id
    @Column(name="sitter_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sitterImageCode;

    @Column(name="sitter_bd_image")
    private String sitterImage;

    @ManyToOne
    @JoinColumn(name="sitter_board_code")
    @JsonIgnore
    private ProductBoard sitterBoardCode;

}