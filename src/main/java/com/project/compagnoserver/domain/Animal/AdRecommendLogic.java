package com.project.compagnoserver.domain.Animal;

import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "ad_recommend_logic")
public class AdRecommendLogic {

    @Id
    @Column(name = "logic_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logicCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private AnimalCategory animalCategory;

    @Column(name = "total_score")
    private Double totalScore;
}
