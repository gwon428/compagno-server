package com.project.compagnoserver.domain.NoticeBoard;

import com.project.compagnoserver.domain.ProductBoard.ProductBoardCommentDTO;
import com.project.compagnoserver.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeBoardCommentDTO {
    private int noticeCommentCode;

    private int noticeBoardCode;

    private String noticeCommentContent;

    private Date noticeCommentRegiDate;

    private User user;

    private int noticeParentCode;

    private Character noticeCommentDelete;

    private List<NoticeBoardCommentDTO> replies = new ArrayList<>();
}
