package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AnimalBoardDAO extends JpaRepository<AnimalBoard, Integer> {

}
