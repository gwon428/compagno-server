package com.project.compagnoserver.domain.ProductBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne
    @JoinColumn(name="product_board_code")
    @JsonIgnoreProperties("bookmark")
    private ProductBoard productBoard;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
