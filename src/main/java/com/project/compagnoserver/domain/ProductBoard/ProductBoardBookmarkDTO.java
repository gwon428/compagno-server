package com.project.compagnoserver.domain.ProductBoard;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBoardBookmarkDTO {

    private int productBookmarkCode;

    private String userId;

    private int productBoardCode;
}
