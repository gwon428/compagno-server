package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, String> {
}
