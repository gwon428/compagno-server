package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.repo.user.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserDAO userDao;

    // 회원가입
    public User create(User user) {
        return userDao.save(user);
    }

    public User login(String id, String password, PasswordEncoder encoder) {
        User user = userDao.findById(id).orElse(null);
        if (user!=null && encoder.matches(password, user.getUserPwd())) {
            log.info("user : " + user);
            return user;
        }
        return null;
    }
}
