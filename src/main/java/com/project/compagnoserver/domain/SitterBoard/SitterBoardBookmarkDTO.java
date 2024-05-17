package com.project.compagnoserver.domain.SitterBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SitterBoardBookmarkDTO {

    private int sitterBookmarkCode;

    private String userId;

    private int sitterBoardCode;
}
