package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.User;
import com.project.compagnoserver.repo.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}
