package com.project.compagnoserver.repo.UserQnaBoard;

import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserQnaQuestionBoardImageDAO extends JpaRepository<UserQnaQuestionBoardImage, Integer> {

    @Query(value="SELECT * FROM user_question_board_image WHERE user_question_board_code = :code", nativeQuery = true)
    List<UserQnaQuestionBoardImage> findByQCode(@Param("code") Integer code);
}
