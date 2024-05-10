package com.project.compagnoserver.domain.AdoptionBoard;


import com.project.compagnoserver.domain.LostBoard.LostBoardCommentDTO;
import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionBoardCommentDTO {

    private int adopCommentCode;
    private String userImg;
    private String userNickname;
    private String commentDate;
    private String commentContent;
    private int adopBoardCode;
    private UserDTO user;
    private List<AdoptionBoardCommentDTO> replies = new ArrayList<>();

}
