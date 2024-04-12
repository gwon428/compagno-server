package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.AnimalBoard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalBoardDAO extends JpaRepository<AnimalBoard, Integer> {
}
