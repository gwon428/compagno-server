package com.project.compagnoserver.repo.LostBoard;

import com.project.compagnoserver.domain.LostBoard.LostBoard;
import com.querydsl.core.annotations.QueryExclude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LostBoardDAO extends JpaRepository<LostBoard, Integer>, QuerydslPredicateExecutor<LostBoard> {

@Modifying
@Transactional
    @Query(value = "UPDATE lost_board SET lost_view_count = lost_view_count+1 WHERE lost_board_code = :lostBoardCode", nativeQuery = true)
    void updateView(@Param("lostBoardCode")int lostBoardCode);


}

