package com.project.compagnoserver.domain.NeighborBoard;
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
@Table(name = "neighbor_board_bookmark")
public class NeighborBoardBookmark {

    @Id
    @Column(name = "neighbor_bookmark_code")
    private int neighborBookmarkCode;

    @Column(name = "neighbor_board_code")
    private int neighborBoardCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "neighbor_bookmark_date")
    private Date neighborBookmarkDate;

}
