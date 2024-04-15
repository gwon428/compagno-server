package com.project.compagnoserver.domain.ProductBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="product_board")
public class ProductBoard {

    @Id
    @Column(name = "product_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productBoardCode;

    @Column(name = "product_board_title")
    private String productBoardTitle;

    @Column(name = "product_main_image")
    private String productMainImage;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_board_grade")
    private float productBoardGrade;

    @Column(name = "product_board_content")
    private String productBoardContent;

    @Column(name = "product_board_regi_date")
    private Date productBoardRegiDate;

    @Column(name = "product_board_view_count")
    private int productBoardViewCount;

    @Column
    private String userId;

    @Column(name="animal_category_code")
    private int animalCategoryCode;

    @OneToMany(mappedBy = "productBoard")
    private List<ProductBoardImage> images;
}
