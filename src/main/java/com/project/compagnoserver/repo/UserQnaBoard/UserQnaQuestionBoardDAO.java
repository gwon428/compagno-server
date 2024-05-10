package com.project.compagnoserver.repo.UserQnaBoard;

import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserQnaQuestionBoardDAO extends JpaRepository<UserQnaQuestionBoard, Integer>, QuerydslPredicateExecutor<UserQnaQuestionBoard> {
}
