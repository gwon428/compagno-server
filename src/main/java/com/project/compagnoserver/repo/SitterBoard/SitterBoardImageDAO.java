package com.project.compagnoserver.repo.SitterBoard;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardImage;
import com.project.compagnoserver.domain.SitterBoard.SitterBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SitterBoardImageDAO extends JpaRepository<SitterBoardImage, Integer> {
    // 각 게시글에 등록된 이미지 전체 조회
    @Query(value = "SELECT * FROM sitter_board_image WHERE sitter_board_code = :code", nativeQuery = true)
    List<SitterBoardImage> findByBoardCode(@Param("code") Integer code);
}
