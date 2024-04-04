package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.User;
import com.project.compagnoserver.repo.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDAO userDAO;

    // 회원가입
    public User create(User user) {
        return userDAO.save(user);
    }

    // 로그인 - 사용자 확인
    public User login(String userId, String userPwd, PasswordEncoder encoder) {
//        Optional<User> findUserId =

       User user = userDAO.findByUserId(userId);
       if(user!=null && encoder.matches(userPwd, user.getUserPwd())) {
           log.info("user : " + user);
           return user;
       }
       return null;
    }
}
