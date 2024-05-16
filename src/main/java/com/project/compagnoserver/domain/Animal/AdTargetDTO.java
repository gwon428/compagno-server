package com.project.compagnoserver.domain.Animal;

import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdTargetDTO {
    private AnimalCategory animalCategory;
    private Double inputValue;
    private int logicCode;
    private Double totalScore;
    private UserDTO user;



    /*
    * animalCategory
:
{animalCategoryCode: 2, animalType: '고양이'}
inputValue
:
0
logicCode
:
2
totalScore
:
0
use
    * */
}
