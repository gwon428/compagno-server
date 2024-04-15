package com.project.compagnoserver.repo.ProductBoard;

import com.project.compagnoserver.domain.ProductBoard.ProductBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductBoardDAO extends JpaRepository<ProductBoard, Integer>, QuerydslPredicateExecutor<ProductBoard> {
}
