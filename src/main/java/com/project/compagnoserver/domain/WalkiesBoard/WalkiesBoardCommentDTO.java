package com.project.compagnoserver.domain.WalkiesBoard;

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
public class WalkiesBoardCommentDTO {

    private int walkiesCommentCode;

    private int walkiesBoardCode;

    private String walkiesCommentContent;

    private Date walkiesCommentRegiDate;

    private User user;

    private List<WalkiesBoardCommentDTO> replies = new ArrayList<>();
}
