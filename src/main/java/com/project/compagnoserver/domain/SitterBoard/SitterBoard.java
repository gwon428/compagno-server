package com.project.compagnoserver.domain.SitterBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Builder
@Table(name="sitter_board")
public class SitterBoard {

    @Id
    @Column(name = "sitter_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sitterBoardCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sitter_category_code")
    @JsonIgnore
    private SitterCategory sitterCategory;

    @Column(name = "sitter_location")
    private String sitterLocation;

    @Column(name = "sitter_title")
    private String sitterTitle;

    @Column(name = "sitter_content")
    private String sitterContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "sitter_view_count")
    private int sitterViewCount;

    @Column(name = "sitter_regi_date")
    private Date sitterRegiDate;

    @Column(name = "sitter_update_date")
    private Date sitterUpdateDate;

    @OneToMany(mappedBy = "sitterBoard")
    private List<SitterBoardImage> images;
}
