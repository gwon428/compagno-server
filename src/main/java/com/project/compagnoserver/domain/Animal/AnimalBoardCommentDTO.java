package com.project.compagnoserver.domain.Animal;

import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalBoardCommentDTO {

    private int animalBoardCode; // 어떤 글에 댓글?
    private int animalCommentCode;
    private String animalCommentContent;
    private Date animalCommentDate;
    private int animalParentCode; // postman 실험용
    private AnimalCategoryDTO animalCategory;
    private UserDTO user;
    private List<AnimalBoardCommentDTO> replies = new ArrayList<>();
}
