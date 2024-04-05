package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.User;
import com.project.compagnoserver.domain.UserDTO;
import com.project.compagnoserver.service.UserService;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    LocalDateTime localDateTime = LocalDateTime.now();
    Date nowDate = java.sql.Timestamp.valueOf(localDateTime);


    @PostMapping("/signUp")
    public ResponseEntity create(@RequestBody User vo) {
        User user = User.builder()
                .userId(vo.getUserId())
                .userPwd(passwordEncoder.encode(vo.getUserPwd()))
                .userPersonName(vo.getUserPersonName())
                .userNickname(vo.getUserNickname())
                .userEmail(vo.getUserEmail())
                .userPhone(vo.getUserPhone())
                .userEnrollDate(nowDate)
                .userStatus("n")
                .userRole("ROLE_USER")
                .build();

        User result = userService.create(user);
        UserDTO dto = UserDTO.builder()
                .userId(result.getUserId())
                .userPersonName(result.getUserPersonName())
                .build();

        return ResponseEntity.ok().body(dto);
    }

}
