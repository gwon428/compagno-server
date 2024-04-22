package com.project.compagnoserver.domain.WalkiesBoard;

import com.project.compagnoserver.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name = "walkies_board_bookmark")
public class WalkiesBoardBookmark {

    @Id
    @Column(name = "walkies_bookmark_code")
    private int walkiesBookmarkCode;

    @Column(name = "walkies_board_code")
    private int walkiesBoardCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "walkies_bookmark_date")
    private Date walkiesBookmarkDate;

}
