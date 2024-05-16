package com.project.compagnoserver.repo.UserQnaBoard;

import com.project.compagnoserver.domain.UserQnaBoard.UserQnaAnswerChoose;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserQnaAnswerChooseDAO extends JpaRepository<UserQnaAnswerChoose, Integer>, QuerydslPredicateExecutor<UserQnaAnswerChoose> {

    @Query(value="SELECT * FROM user_answer_choose WHERE user_question_board_code = :code", nativeQuery = true)
    UserQnaAnswerChoose findByQCode(@Param("code") Integer code);
}
