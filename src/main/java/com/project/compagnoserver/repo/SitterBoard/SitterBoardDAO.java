package com.project.compagnoserver.repo.SitterBoard;

import com.project.compagnoserver.domain.SitterBoard.SitterBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterBoardDAO extends JpaRepository<SitterBoard, Integer> {
}
