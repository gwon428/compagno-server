package com.project.compagnoserver.controller;

import com.project.compagnoserver.config.TokenProvider;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/compagno/*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User vo) {
        User user = userService.login(vo.getUserId(), vo.getPassword(), passwordEncoder);
        if(user!=null) {
            String token = tokenProvider.create(user);

            UserDTO responseDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .userPersonName(user.getUserPersonName())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(responseDTO);
        }

        // 로그인 실패
        return ResponseEntity.badRequest().build();
    }

}
