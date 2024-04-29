package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AnimalBoardDAO extends JpaRepository<AnimalBoard, Integer> {

//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE animal_board SET animal_main_image = :imageUrl WHERE animal_board_code = :boardCode")
//    AnimalBoard updateThumnail(@Param("imageUrl") String imageUrl, @Param("boardCode") Integer boardCode);
}
