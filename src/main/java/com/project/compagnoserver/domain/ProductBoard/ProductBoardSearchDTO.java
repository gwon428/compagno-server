package com.project.compagnoserver.domain.ProductBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBoardSearchDTO {
    private String title;
    private String productName;
    private Integer minPrice;
    private Integer maxPrice;
    private String productCate;
    private Float grade;
    private String nickname;
    private Integer animal;
    private String select;
    private String keyword;
    private String sort;
}
