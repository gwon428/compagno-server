package com.project.compagnoserver.repo.NeighborBoard;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NeighborBoardCommentDAO extends JpaRepository<NeighborBoardComment, Integer> {
    // 각 게시판 전체 댓글 조회
    @Query(value = "SELECT * FROM neighbor_board_comment WHERE neighbor_board_code = :code", nativeQuery = true)
    List<NeighborBoardComment> findByBoardCode(@Param("code") int code);

}
