package com.project.compagnoserver.domain.SitterBoard;

import com.project.compagnoserver.domain.ProductBoard.ProductBoardImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name="sitter_board")
public class SitterBoard {

    @Id
    @Column(name="sitter_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sitterBoardCode;

    @Column(name="sitter_category")
    private String sitterCategory;

    @Column(name="sitter_location")
    private String sitterLocation;

    @Column(name="sitter_title")
    private String sitterTitle;

    @Column(name="sitter_content")
    private String sitterContent;

    @Column(name="user_id")
    private String userId;

    @Column(name="sitter_view_count")
    private int sitterViewCount;

    @Column(name="sitter_regi_date")
    private Date sitterRegiDate;

    @Column(name="sitter_update_date")
    private Date sitterUpdateDate;

    @Column(name="sitter_status")
    private String sitterStatus;

    @OneToMany(mappedBy = "sitterBoardCode")
    private List<SitterBoardImage> sitterImages;
}
