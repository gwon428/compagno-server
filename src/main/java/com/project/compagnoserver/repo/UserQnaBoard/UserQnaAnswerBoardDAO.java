package com.project.compagnoserver.repo.UserQnaBoard;

import com.project.compagnoserver.domain.UserQnaBoard.UserQnaAnswerBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserQnaAnswerBoardDAO extends JpaRepository<UserQnaAnswerBoard, Integer>, QuerydslPredicateExecutor<UserQnaAnswerBoard> {
}
