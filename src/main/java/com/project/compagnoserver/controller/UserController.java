package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.User;
import com.project.compagnoserver.domain.UserDTO;
import com.project.compagnoserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signUp")
    public ResponseEntity create(@RequestBody User vo) {

        User user = User.builder()
                        .userId(vo.getUserId())
                        .userPwd(passwordEncoder.encode(vo.getPassword()))
                        .userName(vo.getUsername())
                        .userNickname(vo.getUserNickname())
                        .userEmail(vo.getUserEmail())
                        .userPhone(vo.getUserPhone())
                        .userRole("ROLE_USER")
                        .build();

        User result = userService.create(user);
        UserDTO responseDTO = UserDTO.builder()
                                        .userId(result.getUserId())
                                        .userName(result.getUsername())
                                        .build();

        return ResponseEntity.ok().body(responseDTO);
    }

}
