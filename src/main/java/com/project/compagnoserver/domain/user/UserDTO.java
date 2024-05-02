package com.project.compagnoserver.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String token;

    private String userId;
    private String userPersonName;
    private String userPwd;

    // LostBoardComment에 필요
    private String userNickname;
    private String userImg;
    private String userRole;

    private String userPhone;
    private String userEmail;

    private String userStatus;

    private MultipartFile file;

    private Integer defaultImg;

}
