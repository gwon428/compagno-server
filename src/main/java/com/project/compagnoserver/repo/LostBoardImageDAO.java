package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.LostBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostBoardImageDAO extends JpaRepository<LostBoardImage, Integer> {
}
