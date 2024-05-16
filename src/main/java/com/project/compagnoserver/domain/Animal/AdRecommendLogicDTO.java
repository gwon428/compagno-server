package com.project.compagnoserver.domain.Animal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdRecommendLogicDTO {
    private AdTargetDTO target;
    private Double response;
    private AdTargetDTO exception;
    private Double inputValue;

}
