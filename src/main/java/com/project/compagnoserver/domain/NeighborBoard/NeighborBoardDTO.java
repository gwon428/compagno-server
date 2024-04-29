package com.project.compagnoserver.domain.NeighborBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class NeighborBoardDTO {

    private int neighborBoardCode;

    private int animalCategoryCode;

    private int locationCode;

    private String neighborBoardTitle;

    private String neighborBoardContent;

    private String userId;

    private Date neighborBoardRegiDate;

    private List<MultipartFile> files;
    private List<String> images;
}

