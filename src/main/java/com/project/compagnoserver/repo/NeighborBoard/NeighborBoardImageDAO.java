package com.project.compagnoserver.repo.NeighborBoard;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NeighborBoardImageDAO extends JpaRepository<NeighborBoardImage, Integer> {
    // 각 게시글에 등록된 이미지 전체 조회
    @Query(value = "SELECT * FROM neighbor_board_image WHERE neighbor_board_code = :code", nativeQuery = true)
    List<NeighborBoardImage> findByBoardCode(@Param("code") Integer code);
}
