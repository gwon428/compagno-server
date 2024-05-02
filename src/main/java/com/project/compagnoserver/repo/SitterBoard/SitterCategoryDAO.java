package com.project.compagnoserver.repo.SitterBoard;

import com.project.compagnoserver.domain.SitterBoard.SitterCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SitterCategoryDAO  extends JpaRepository<SitterCategory, Integer>, QuerydslPredicateExecutor<SitterCategory> {
}
