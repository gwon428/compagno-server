package com.project.compagnoserver.domain.AdoptionBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="adoption_board_image")
public class AdoptionBoardImage {

    @Id
    @Column(name="adoption_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adopImageCode;

    @ManyToOne
    @JoinColumn(name="adoption_board_code")
    @JsonIgnore
    private AdoptionBoard adopBoardCode;

    @Column(name="adoption_image")
    private String adopImage;

}
