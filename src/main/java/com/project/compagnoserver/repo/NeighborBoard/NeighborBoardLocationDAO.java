package com.project.compagnoserver.repo.NeighborBoard;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NeighborBoardLocationDAO extends JpaRepository<NeighborBoardLocation, Integer> {
    // 시도에 따른 시군구 전체 조회
    @Query(value = "SELECT * FROM location WHERE location_parent_code = :code", nativeQuery = true)
    List<NeighborBoardLocation> findByParentCode(@Param("code") int code);
}
