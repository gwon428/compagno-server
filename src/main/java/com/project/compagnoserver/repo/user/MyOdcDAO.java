package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MyOdcDAO extends JpaRepository<ClassBoard, Integer>, QuerydslPredicateExecutor<ClassBoard> {
}
