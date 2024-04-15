package com.project.compagnoserver.repo.Qna;

import com.project.compagnoserver.domain.QnaA.QnaABoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QnaABoardDAO extends JpaRepository<QnaABoard, Integer>, QuerydslPredicateExecutor<QnaABoard> {
}
