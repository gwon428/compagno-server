package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.user.User;
import org.jetbrains.annotations.Async;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User, String> {

    // ID 중복검사
    @Query(value = "SELECT count(user_id) AS result FROM user WHERE user_id = :id", nativeQuery = true)
    Integer checkDuplicationId(@Param("id") String id);

    // 닉네임 중복검사
    @Query(value = "SELECT count(user_nickname) AS result FROM user WHERE user_nickname = :nickname", nativeQuery = true)
    Integer checkDuplicationNick(@Param("nickname") String nickname);

    // 이메일, 비밀번호 변경
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user SET user_email = :email, user_phone = :phone, user_pwd = :password WHERE user_id = :id", nativeQuery = true)
    void updateUserInfo(@Param("email") String email, @Param("phone") String phone, @Param("password") String password , @Param("id") String id);

    // 회원 탈퇴
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user SET user_status = \"y\" , user_quit_date = CURRENT_TIMESTAMP WHERE user_id = :id", nativeQuery = true)
    void deleteUserInfo(@Param("id") String id);

    // 비밀번호 변경을 위한 비밀번호 체크
    @Query(value = "SELECT count(*) FROM user where user_id = :id and user_pwd = :password", nativeQuery = true)
    Integer checkPassword(@Param("id") String id, @Param("password") String password);
}
