package com.project.compagnoserver.domain.LostBoard;

import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostBoardCommentDTO {

    private int lostCommentCode;
    private String userImg;
    private String userNickname;
    private Timestamp commentDate;
    private String commentContent;
    private int lostBoardCode;
    private UserDTO user;
    private List<LostBoardCommentDTO> replies = new ArrayList<>();

}
