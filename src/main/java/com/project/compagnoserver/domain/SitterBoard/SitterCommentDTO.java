package com.project.compagnoserver.domain.SitterBoard;

import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class SitterCommentDTO {

    private int sitterCommentCode;
    private int sitterBoardCode;
    private UserDTO user;
    private String sitterCommentContent;
    private Date sitterCommentRegiDate;
    private String sitterCommentStatus;
    private Date sitterCommentDelDate;
    private List<SitterCommentDTO> sitterReplies = new ArrayList<>();

}
