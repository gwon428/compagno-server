package com.project.compagnoserver.domain.NeighborBoard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne
    @JoinColumn(name = "neighbor_board_code")
    @JsonIgnoreProperties("bookmark")
    private NeighborBoard neighborBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Column(name = "neighbor_bookmark_date")
//    private Date neighborBookmarkDate;

}
