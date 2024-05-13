package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MyNeighborPostDAO extends JpaRepository<NeighborBoard, Integer>, QuerydslPredicateExecutor<NeighborBoard> {
}
