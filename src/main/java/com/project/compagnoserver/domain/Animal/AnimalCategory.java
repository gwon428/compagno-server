package com.project.compagnoserver.domain.Animal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "animal_category")
public class AnimalCategory {
    @Id
    @Column(name = "animal_category_code")
    private int animalCategoryCode;

    @Column(name = "animal_type")
    private String animalType;

    // 조회와 관련된 부분 쓸때는 그냥 카테코드만 넘겨주고, 조회때 신경쓰면 됨.
}
