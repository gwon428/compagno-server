package com.project.compagnoserver.domain.SitterBoard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="sitter_board_bookmark")
public class SitterBoardBookmark {

    @Id
    @Column(name = "sitter_bookmark_code")
    private int sitterBookmarkCode;

    @ManyToOne
    @JoinColumn(name = "sitter_board_code")
    @JsonIgnoreProperties("bookmark")
    private SitterBoard sitterBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
