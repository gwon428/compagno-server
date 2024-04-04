package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.User;
import com.project.compagnoserver.repo.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User create(User user) {
        return userDAO.save(user);
    }
}
