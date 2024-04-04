package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
}
