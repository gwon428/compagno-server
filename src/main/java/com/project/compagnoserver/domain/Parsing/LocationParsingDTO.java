package com.project.compagnoserver.domain.Parsing;

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
public class LocationParsingDTO {

    private int locationCode;
    private String locationName;
    private List<LocationParsingDTO> districts = new ArrayList<>();

}
