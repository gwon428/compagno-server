package com.project.compagnoserver.domain.ProductBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBoardDTO {

    private int productBoardCode;

    private String productBoardTitle;

    private MultipartFile productMainFile;
    private String mainImage;

    private String productName;

    private int productPrice;

    private String productCategory;

    private float productBoardGrade;

    private String productBoardContent;

    private String userId;

    private int animalCategoryCode;

    private List<MultipartFile> files;
    private List<String> images;
}
