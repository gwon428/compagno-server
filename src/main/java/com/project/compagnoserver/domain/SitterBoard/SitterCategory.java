package com.project.compagnoserver.domain.SitterBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "sitter_category")
public class SitterCategory {

    @Id
    @Column(name = "sitter_category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sitterCategoryCode;

    @Column(name = "sitter_category_type")
    private String sitterCategoryType;

}
