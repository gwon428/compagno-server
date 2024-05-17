package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.SitterBoard.SitterBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MySitterComDAO extends JpaRepository<SitterBoardComment, Integer>, QuerydslPredicateExecutor<SitterBoardComment> {
}
