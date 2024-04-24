package com.project.compagnoserver.repo.Qna;

import com.project.compagnoserver.domain.QnaA.QnaABoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaABoardDAO extends JpaRepository<QnaABoard, Integer>, QuerydslPredicateExecutor<QnaABoard> {

    @Query(value="SELECT * FROM qna_a_board WHERE qna_q_code= :code", nativeQuery = true)
    QnaABoard findByqnaQCode(@Param("code") int code);
}
