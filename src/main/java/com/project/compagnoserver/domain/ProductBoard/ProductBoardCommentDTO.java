package com.project.compagnoserver.domain.ProductBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import jakarta.persistence.*;
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
public class ProductBoardCommentDTO {

    private int productCommentCode;

    private int productBoardCode;

    private String productCommentContent;

    private Date productCommentRegiDate;

    private User user;

    private int productParentCode;

    private Character productCommentDelete;

    private List<ProductBoardCommentDTO> replies = new ArrayList<>();
}
