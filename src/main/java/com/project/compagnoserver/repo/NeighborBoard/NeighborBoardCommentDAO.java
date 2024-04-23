package com.project.compagnoserver.repo.NeighborBoard;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeighborBoardCommentDAO extends JpaRepository<NeighborBoardComment, Integer> {
}
