package com.project.compagnoserver.domain.Animal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalCategoryDTO {

    private int animalCategoryCode;
    private String animalType;
}
