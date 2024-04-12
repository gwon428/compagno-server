package com.project.compagnoserver.repo.QnaQ;

import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QnaQBoardDAO extends JpaRepository<QnaQBoard, Integer>, QuerydslPredicateExecutor<QnaQBoard> {
}
