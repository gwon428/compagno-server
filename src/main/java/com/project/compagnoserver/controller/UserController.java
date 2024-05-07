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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
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


    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity create(@RequestBody User vo) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String nowDate = java.sql.Timestamp.valueOf(localDateTime).toString();

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
            
            // 가입일에서 년-월-일 만 표기
            String[] showEnrollDate = user.getUserEnrollDate().split(" ");

            UserDTO responseDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .userPersonName(user.getUserPersonName())
                    .userRole(user.getUserRole())
                    .userImg(user.getUserImg())
                    .userNickname(user.getUserNickname())
                    .userPhone(user.getUserPhone())
                    .userEmail(user.getUserEmail())
                    .userStatus(user.getUserStatus())
                    .userEnrollDate(showEnrollDate[0])
                    .token(token)
                    .build();
            log.info("user : " + responseDTO);
            return ResponseEntity.ok().body(responseDTO);
        }

        // 로그인 실패
        return ResponseEntity.badRequest().build();
    }

    // 마이페이지 내 정보 가져오기
    @GetMapping("/api/mypage/myinfo/{id}")
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

    // 회원 탈퇴
    @Transactional
    @PutMapping("/api/mypage/myinfo/quit")
    public ResponseEntity quitUser(@RequestBody User vo) {

            // result가 1 이면 회원탈퇴 성공, 0 이면 회원탈퇴 조건 미충족으로 실패
           int result =  userService.deleteUser(vo.getUserId(), vo.getUserPwd(), passwordEncoder);
           if(result == 1) {

               return ResponseEntity.ok().build();
           }
           return ResponseEntity.badRequest().build();
   }

    // 개인정보 + 프사 변경
    @Transactional
    @PutMapping("/api/mypage/myinfo/updateProfile")
    public ResponseEntity changeProfile(UserDTO dto) throws IOException, ParseException {

        User user = User.builder()
                .userEmail(dto.getUserEmail())
                .userPhone(dto.getUserPhone())
                .userPwd(passwordEncoder.encode(dto.getUserPwd()))
                .userId(dto.getUserId())
                .build();

//        // 프로필사진도 변경할때
       if(dto.getFile()!=null ) {
            String fileName = dto.getFile().getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

           String saveName = "user" + File.separator + uuid + "_" + fileName;

            String saveNameWithPath = uploadPath + File.separator + "user" + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveNameWithPath);
            dto.getFile().transferTo(savePath);

            user.setUserImg(saveName);

            userService.changeProfile(user);
        }

       // 기본 프로필사진으로 변경할때
        else if(dto.getFile()==null && dto.getDefaultImg() == 1) {
            String saveName2 = "user" + File.separator + "defaultImage.png";
           user.setUserImg(saveName2);

           userService.changeProfile(user);
       }

       else {
           userService.updateUser(user);
       }

        return ResponseEntity.ok().build();
    }

}
