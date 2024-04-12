package com.project.compagnoserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="lost_board_image")
public class LostBoardImage {

    @Id
    @Column(name="lost_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lostImageCode;

    @ManyToOne
    @JoinColumn(name="lost_board_code")
    @JsonIgnore
    private LostBoard lostBoardCode;

    @Column(name="lost_image")
    private String lostImage;

}
