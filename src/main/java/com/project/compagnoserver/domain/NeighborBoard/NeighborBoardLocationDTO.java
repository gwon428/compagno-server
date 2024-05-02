package com.project.compagnoserver.domain.NeighborBoard;

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
public class NeighborBoardLocationDTO {

    private int locationCode;
    private String locationName;
    private List<NeighborBoardLocationDTO> districts = new ArrayList<>();

}
