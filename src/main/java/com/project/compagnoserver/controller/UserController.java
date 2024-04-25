package com.project.compagnoserver.controller;

import com.project.compagnoserver.config.TokenProvider;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.domain.user.UserDTO;
import com.project.compagnoserver.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    LocalDateTime localDateTime = LocalDateTime.now();
    Date nowDate = java.sql.Timestamp.valueOf(localDateTime);


    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity create(@RequestBody User vo) {
        String uuid = UUID.randomUUID().toString();

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
                .userImg("user" + File.separator + "defaultImage.png") // LostBoardComment 확인 위해
                .build();

        User result = userService.create(user);
        UserDTO dto = UserDTO.builder()
                .userId(result.getUserId())
                .userPersonName(result.getUserPersonName())
                .build();

        return ResponseEntity.ok().body(dto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User vo) {
        User user = userService.login(vo.getUserId(), vo.getPassword(), passwordEncoder);
        if(user!=null) {
            String token = tokenProvider.create(user);

            UserDTO responseDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .userPersonName(user.getUserPersonName())
                    .userRole(user.getUserRole())
                    .userImg(user.getUserImg())
                    .userNickname(user.getUserNickname())
                    .userPhone(user.getUserPhone())
                    .userEmail(user.getUserEmail())
                    .userStatus(user.getUserStatus())
                    .token(token)
                    .build();
            log.info("user : " + responseDTO);
            return ResponseEntity.ok().body(responseDTO);
        }

        // 로그인 실패
        return ResponseEntity.badRequest().build();
    }

    // 마이페이지 내 정보 가져오기
    @GetMapping("/myinfo/{id}")
    public ResponseEntity myPageInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.myPageInfo(id));
    }

    // ID 중복검사
    @GetMapping("/signUp/checkid/{id}")
    public ResponseEntity checkUserId(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.checkUserId(id));
    }

    // 닉네임 중복검사
    @GetMapping("/signUp/checknick/{nickname}")
    public ResponseEntity checkUserNick(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(userService.checkUserNick(nickname));
    }

    // 이메일 변경
    @Transactional
    @PutMapping("/mypage/myinfo")
    public ResponseEntity updateUser(@RequestBody User vo) {
        User user = User.builder()
                .userEmail(vo.getUserEmail())
                .userPhone(vo.getUserPhone())
                .userPwd(passwordEncoder.encode(vo.getUserPwd()))
                .userId(vo.getUserId())
                .build();

       log.info("컨트롤러에서 입력값 : " + user);
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
   }

}
