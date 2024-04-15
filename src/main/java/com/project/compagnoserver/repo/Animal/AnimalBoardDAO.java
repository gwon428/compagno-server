package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalBoardDAO extends JpaRepository<AnimalBoard, Integer> {
}
