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

    public User myPageInfo(String id) {
        User user = userDao.findById(id).orElse(null);
        return user;
    }

    // ID 중복검사
    public int checkUserId(String id) {
    Integer checkIdResult = userDao.checkDuplicationId(id);
    return checkIdResult;
    }

    // 닉네임 중복검사
    public int checkUserNick(String nickname) {
        Integer checkNickResult = userDao.checkDuplicationNick(nickname);
        log.info("입력값 : " + nickname);
        log.info("닉네임조회 결과 : " + checkNickResult);
        return checkNickResult;
    }

  // 회원 탈퇴
    public int deleteUser(String id, String password, PasswordEncoder encoder) {
      User user = userDao.findById(id).orElse(null);
      if(user!= null && encoder.matches(password, user.getPassword())) {
          userDao.deleteUserInfo(id);
          // 1 리턴하면 회원탈퇴 성공
          return 1;
      }
        // 0 리턴하면 회원정보 틀려서 탈퇴 실패
        return 0;
    }

    // 프사제외 개인정보 변경
    public void updateUser(User user) {
        userDao.updateUserInfo(user.getUserEmail(), user.getUserPhone(), user.getUserPwd(), user.getUserId());
    }

    // 개인정보 + 프로필 변경
    public void changeProfile(User user) {
        userDao.changeProfile(user.getUserEmail(), user.getUserPhone(), user.getUserPwd(), user.getUserImg(), user.getUserId());
    }



}
