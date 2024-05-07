package com.project.compagnoserver.repo.Qna;

import com.project.compagnoserver.domain.QnaA.QnaABoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaABoardImageDAO extends JpaRepository<QnaABoardImage, Integer>  {

    @Query(value="SELECT * FROM qna_a_board_image WHERE qna_a_code= :code", nativeQuery = true)
    List<QnaABoardImage> findByQnaACode(@Param("code") Integer code);
}
