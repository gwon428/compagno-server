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



    // 임시 테스트용
    private String userNickname;
    private String userImg;
}
