package com.project.compagnoserver.repo.AdoptionBoard;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoard;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AdoptionBoardDAO extends JpaRepository<AdoptionBoard, Integer>, QuerydslPredicateExecutor<AdoptionBoard> {
}
