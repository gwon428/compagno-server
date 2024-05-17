package com.project.compagnoserver.repo.SitterBoard;

import com.project.compagnoserver.domain.SitterBoard.SitterBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterBoardBookmarkDAO extends JpaRepository<SitterBoardBookmark, Integer> {
}
