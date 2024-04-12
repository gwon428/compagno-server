package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.QnaQBoard;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QnaQBoardDAO extends JpaRepository<QnaQBoard, Integer>, QuerydslPredicateExecutor<QnaQBoard> {
}
