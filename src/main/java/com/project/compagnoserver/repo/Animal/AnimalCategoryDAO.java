package com.project.compagnoserver.repo.Animal;


import com.project.compagnoserver.domain.Animal.AnimalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AnimalCategoryDAO extends JpaRepository<AnimalCategory, Integer>, QuerydslPredicateExecutor<AnimalCategory> {


}
