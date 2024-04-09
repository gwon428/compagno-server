package com.project.compagnoserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "animal_board_image")
public class AnimalBoardImage {

    @Id
    @Column(name = "a_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aImageCode;

    @ManyToOne
    @JoinColumn(name = "a_board_code")
    @JsonIgnore
    private AnimalBoard animalBoard;

    @Column(name = "a_board_image")
    private String aBoardImage;
}
