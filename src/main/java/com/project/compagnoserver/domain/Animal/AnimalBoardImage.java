package com.project.compagnoserver.domain.Animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "animal_board_image")
public class AnimalBoardImage {

    @Id
    @Column(name = "animal_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalImageCode;

    @ManyToOne
    @JoinColumn(name = "animal_board_code")
    @JsonIgnore
    private AnimalBoard animalBoard;

    @Column(name = "animal_board_image")
    private String animalBoardImage;
}

/*
* animal_image_code INT PRIMARY KEY AUTO_INCREMENT,
    animal_board_code int,
    animal_board_image varchar(300)
* */
