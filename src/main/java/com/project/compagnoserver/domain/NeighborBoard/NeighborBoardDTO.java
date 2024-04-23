package com.project.compagnoserver.domain.NeighborBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class NeighborBoardDTO {

    private int neighborBoardCode;

    private int animalCategoryCode;

    private String neighborLocation;

    private String neighborBoardTitle;

    private String neighborBoardContent;

    private String userId;

    private List<MultipartFile> files;
    private List<String> images;
}

