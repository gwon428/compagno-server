package com.project.compagnoserver.repo.LostBoard;

import com.project.compagnoserver.domain.LostBoard.LostBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostBoardImageDAO extends JpaRepository<LostBoardImage, Integer> {
}
