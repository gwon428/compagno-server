package com.project.compagnoserver.domain.Animal;

import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalBoardFavoriteDTO {

//    private UserDTO user;
    private int animal_favorite_code;
    private String userId;
    private int animalBoardCode;
    private Date animalFavoriteDate;
    private boolean checkBoolean;

}
