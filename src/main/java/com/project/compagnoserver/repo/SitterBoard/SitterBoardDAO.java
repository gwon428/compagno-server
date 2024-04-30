package com.project.compagnoserver.repo.SitterBoard;

import com.project.compagnoserver.domain.SitterBoard.SitterBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface SitterBoardDAO extends JpaRepository<SitterBoard, Integer>, QuerydslPredicateExecutor<SitterBoard> {
    // 카테고리(구인/구직)별 게시글 조회
    @Query(value = "SELECT * FROM sitter_board WHERE sitter_category_code=:code", nativeQuery = true)
    Page<SitterBoard> findBySitterCateCode(@Param("code") Integer code, Pageable pageable);

    // 동물 카테고리 별 게시글 조회
    @Query(value = "SELECT * FROM sitter_board WHERE animal_category_code=:code", nativeQuery = true)
    Page<SitterBoard> findByAnimalCateCode(@Param("code") Integer code, Pageable pageable);

    // 지역별 게시글 조회
    @Query(value = "SELECT * FROM sitter_board WHERE location_code=:code", nativeQuery = true)
    Page<SitterBoard> findByLocationCode(@Param("code") Integer code, Pageable pageable);
}
