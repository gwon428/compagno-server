package com.project.compagnoserver.domain.SitterBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data @NoArgsConstructor @AllArgsConstructor
public class SitterCategoryDTO {

    private int sitterCategoryCode;
    private String sitterCategoryType;
}
