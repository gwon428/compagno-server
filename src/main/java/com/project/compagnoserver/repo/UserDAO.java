package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE user_id = :userId", nativeQuery = true)
    User findByUserId(@Param("userId") String userId);
}
