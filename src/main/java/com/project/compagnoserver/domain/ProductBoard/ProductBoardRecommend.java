package com.project.compagnoserver.domain.ProductBoard;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="product_board_recommend")
public class ProductBoardRecommend {

    @Id
    @Column(name = "product_recommend_code")
    private int productRecommendCode;

    @Column(name = "product_recommend_date")
    private Date productRecommendDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="product_board_code")
    @JsonIgnore
    private ProductBoard productBoard;
}
