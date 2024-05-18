package com.project.compagnoserver.domain.NeighborBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NeighborBoardBookmarkDTO {

    private int neighborBookmarkCode;

    private String userId;

    private int neighborBoardCode;

}
