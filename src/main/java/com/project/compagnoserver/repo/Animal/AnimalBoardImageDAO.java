package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalBoardImageDAO extends JpaRepository<AnimalBoardImage, Integer> {
    @Query(value = "SELECT * FROM animal_board_image WHERE animal_board_code = :boardCode", nativeQuery = true)
    List<AnimalBoardImage> prevImages(@Param("boardCode") int boardCode);
}
