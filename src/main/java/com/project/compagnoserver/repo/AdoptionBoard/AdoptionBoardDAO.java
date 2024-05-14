package com.project.compagnoserver.repo.AdoptionBoard;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoard;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AdoptionBoardDAO extends JpaRepository<AdoptionBoard, Integer>, QuerydslPredicateExecutor<AdoptionBoard> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE adoption_board SET adoption_view_count = adoption_view_count+1 WHERE adoption_board_code = :adopBoardCode", nativeQuery = true)
    void updateView(@Param("adopBoardCode")int adopBoardCode);




}
