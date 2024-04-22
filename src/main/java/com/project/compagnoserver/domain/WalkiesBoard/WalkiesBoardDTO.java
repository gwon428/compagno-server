package com.project.compagnoserver.domain.WalkiesBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class WalkiesBoardDTO {

    private int walkiesBoardCode;

    private int animalCategoryCode;

    private String walkiesLocation;

    private String walkiesBoardTitle;

    private String walkiesBoardContent;

    private String userId;

    private List<MultipartFile> files;
    private List<String> images;
}
