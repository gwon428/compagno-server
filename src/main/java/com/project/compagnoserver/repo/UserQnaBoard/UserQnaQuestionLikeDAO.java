package com.project.compagnoserver.repo.UserQnaBoard;

import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoardImage;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserQnaQuestionLikeDAO extends JpaRepository<UserQnaQuestionLike, Integer>, QuerydslPredicateExecutor<UserQnaQuestionLike> {
}
