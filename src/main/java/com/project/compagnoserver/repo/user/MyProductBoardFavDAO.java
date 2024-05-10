package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.ProductBoard.ProductBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MyProductBoardFavDAO extends JpaRepository<ProductBoardBookmark, Integer>, QuerydslPredicateExecutor<ProductBoardBookmark> {
}
