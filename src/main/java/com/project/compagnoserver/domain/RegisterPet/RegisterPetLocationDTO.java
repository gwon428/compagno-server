package com.project.compagnoserver.domain.RegisterPet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterPetLocationDTO {

    private int locationCode;
    private String locationName;
    private List<RegisterPetLocationDTO> districts = new ArrayList<>();

}
