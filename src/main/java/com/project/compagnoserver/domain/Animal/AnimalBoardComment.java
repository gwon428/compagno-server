package com.project.compagnoserver.domain.Animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Builder
@Table(name = "animal_board_comment")
public class AnimalBoardComment {

    @Id
    @Column(name = "animal_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalCommentCode;

    @ManyToOne
    @JoinColumn(name = "animal_board_code")
    private AnimalBoard animalBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "animal_comment_content")
    private String animalCommentContent;

    @Column(name = "animal_comment_date")
    private Date animalCommentDate;

    @Column(name = "animal_parent_code")
    private  int animalParentCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "animal_parent_code" ,referencedColumnName = "animal_comment_code", insertable = false, updatable = false)
    private AnimalBoardComment animalParent;
}
/*

    animal_comment_code INT PRIMARY KEY AUTO_INCREMENT,
    animal_board_code INT,
    user_id VARCHAR(20),
    animal_comment_content VARCHAR(50),
    animal_comment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    animal_parent_code INT

* */