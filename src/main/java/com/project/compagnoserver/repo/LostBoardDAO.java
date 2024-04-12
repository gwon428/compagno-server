package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.LostBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostBoardDAO extends JpaRepository<LostBoard, Integer> {
}
