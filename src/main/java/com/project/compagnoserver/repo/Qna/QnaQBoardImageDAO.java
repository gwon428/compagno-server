package com.project.compagnoserver.repo.Qna;

import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaQBoardImageDAO extends JpaRepository<QnaQBoardImage, Integer> {

    @Query(value="SELECT * FROM qna_q_board_image WHERE qna_q_code= :code", nativeQuery = true)
    List<QnaQBoardImage> findByqnaQCode(@Param("code") Integer code);
}
