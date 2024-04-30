package com.project.compagnoserver.domain.SitterBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SitterBoardSearchDTO {

    private String select;
    private String keyword;

}
