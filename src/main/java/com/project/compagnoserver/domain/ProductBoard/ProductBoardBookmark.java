package com.project.compagnoserver.domain.ProductBoard;

import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="product_board_bookmark")
public class ProductBoardBookmark {

    @Id
    @Column(name = "product_bookmark_code")
    private int productBookmarkCode;

    @Column(name = "product_board_code")
    private int productBoardCode;

    @Column(name = "user_id")
    private String userId;
}
