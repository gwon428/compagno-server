package com.project.compagnoserver.repo.LostBoard;

import com.project.compagnoserver.domain.LostBoard.LostBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostBoardDAO extends JpaRepository<LostBoard, Integer> {
}
