package com.project.compagnoserver.repo.QnaQ;

import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QnaQBoardImageDAO extends JpaRepository<QnaQBoardImage, Integer> {
}
