package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalCategory;
import com.project.compagnoserver.domain.Animal.QAdRecommendLogic;
import com.project.compagnoserver.domain.Animal.QAnimalCategory;
import com.project.compagnoserver.domain.user.QUser;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.repo.Animal.AdRecommendLogicDAO;
import com.project.compagnoserver.repo.user.UserDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserDAO userDao;
    @Autowired
    private AdRecommendLogicDAO logicDAO;

    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private EntityManager entityManager;
    private final QAnimalCategory qAnimalCategory = QAnimalCategory.animalCategory;


    public List<AnimalCategory> categoryList(){
        List<AnimalCategory> categoryList = queryFactory.selectFrom(qAnimalCategory).fetch();
        return categoryList;
    }
    // 회원가입
    public User create(User user) {
        return userDao.save(user);
    }
    // 회원가입 후 추천로직 기본세팅
    public void setLogic (User user){
        List<AnimalCategory> category = categoryList();
        for(AnimalCategory oneCategory : category){

            logicDAO.setLogicBase(user.getUserId(), oneCategory.getAnimalCategoryCode());
        }
    }


    public User login(String id, String password, PasswordEncoder encoder) {
        User user = userDao.findById(id).orElse(null);
        if (user!=null && encoder.matches(password, user.getUserPwd())) {
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

    // 프사제외 개인정보 변경 user.getUserPwd()
    public void updateUser(User user) {
        userDao.updateUserInfo(user.getUserEmail(), user.getUserPhone(), user.getUserId());
    }

    // 개인정보 + 프로필 변경
    public void changeProfile(User user) {
        userDao.changeProfile(user.getUserEmail(), user.getUserPhone(), user.getUserImg(), user.getUserId());
    }

    // 비밀번호 변경
    public void changePwd(User user) {
        QUser qUser = QUser.user;
        queryFactory.update(qUser)
                .set(qUser.userPwd, user.getUserPwd())
                .where(qUser.userId.eq(user.getUserId()))
                .execute();
    }
}
