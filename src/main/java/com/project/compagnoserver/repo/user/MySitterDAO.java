package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.SitterBoard.SitterBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MySitterDAO extends JpaRepository<SitterBoard, Integer>, QuerydslPredicateExecutor<SitterBoard> {
}
