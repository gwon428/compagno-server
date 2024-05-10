package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MyPageQnaDAO extends JpaRepository<QnaQBoard, Integer>, QuerydslPredicateExecutor<QnaQBoard> {
}
