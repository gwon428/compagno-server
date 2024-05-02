package com.project.compagnoserver.domain.Animal;

import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "animal_board_favorite")
@DynamicInsert
public class AnimalBoardFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_favorite_code")
    private  int animalFavoriteCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_board_code")
    private AnimalBoard animalBoard;

    @Column(name = "animal_favorite_date")
    private Date animalFavoriteDate;
}
