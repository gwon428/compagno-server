package com.project.compagnoserver.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String token;

    private String userId;
    private String userPersonName;

    // LostBoardComment에 필요
    private String userNickname;
    private String userImg;
    private String userRole;

    private String userPhone;
    private String userEmail;

    private String userStatus;
}
