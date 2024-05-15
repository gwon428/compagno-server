package com.project.compagnoserver.repo.UserQnaBoard;

import com.project.compagnoserver.domain.UserQnaBoard.UserQnaAnswerChoose;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface UserQnaQuestionBoardDAO extends JpaRepository<UserQnaQuestionBoard, Integer>, QuerydslPredicateExecutor<UserQnaQuestionBoard> {

}
