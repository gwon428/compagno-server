package com.project.compagnoserver.repo.user;


import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MyAdoptionDAO extends JpaRepository<AdoptionBoard, Integer>, QuerydslPredicateExecutor<AdoptionBoard> {
}
