package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.LostBoard.LostBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MyLostDAO extends JpaRepository<LostBoard, Integer>, QuerydslPredicateExecutor<LostBoard> {
}
