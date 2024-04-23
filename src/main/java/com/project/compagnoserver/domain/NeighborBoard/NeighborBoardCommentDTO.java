package com.project.compagnoserver.domain.NeighborBoard;

import com.project.compagnoserver.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class NeighborBoardCommentDTO {

    private int neighborCommentCode;

    private int neighborBoardCode;

    private String neighborCommentContent;

    private Date neighborCommentRegiDate;

    private User user;

    private List<NeighborBoardCommentDTO> replies = new ArrayList<>();
}

