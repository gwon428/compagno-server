package com.project.compagnoserver.domain.SitterBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "sitter_category")
public class SitterCategory {

    @Id
    @Column(name = "sitter_category_code")
    private int sitterCategoryCode;

    @Column(name = "sitter_category_type")
    private String sitterCategoryType;

}
