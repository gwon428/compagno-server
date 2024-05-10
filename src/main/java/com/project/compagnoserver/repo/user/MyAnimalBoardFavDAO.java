package com.project.compagnoserver.repo.user;

import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface MyAnimalBoardFavDAO extends JpaRepository<AnimalBoardFavorite, Integer>, QuerydslPredicateExecutor<AnimalBoardFavorite> {


}
