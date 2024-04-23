package com.project.compagnoserver.repo.NeighborBoard;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NeighborBoardDAO extends JpaRepository<NeighborBoard, Integer>, QuerydslPredicateExecutor<NeighborBoard> {
}
