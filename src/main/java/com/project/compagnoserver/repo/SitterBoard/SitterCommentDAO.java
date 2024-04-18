package com.project.compagnoserver.repo.SitterBoard;


import com.project.compagnoserver.domain.SitterBoard.SitterBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SitterCommentDAO extends JpaRepository<SitterBoardComment, Integer> {

    // 각 게시판 전체 댓글 조회
    @Query(value = "SELECT * FROM sitter_board_comment WHERE sitter_board_code = :code", nativeQuery = true)
    List<SitterBoardComment> findBySitterBoardCode(@Param("code") int code);

}
