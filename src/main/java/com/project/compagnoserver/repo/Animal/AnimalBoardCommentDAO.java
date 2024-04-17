package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalBoardCommentDAO extends JpaRepository<AnimalBoardComment, Integer> {
}
