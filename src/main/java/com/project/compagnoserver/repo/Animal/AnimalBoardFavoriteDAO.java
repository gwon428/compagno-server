package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimalBoardFavoriteDAO extends JpaRepository<AnimalBoardFavorite,Integer> {
    @Query(value = "SELECT * FROM animal_board_favorite WHERE animal_board_code = :boardCode AND user_id = :userId",nativeQuery = true)
    AnimalBoardFavorite checkFavorite(@Param("boardCode") int boardCode, @Param("userId") String userId);

//    @Query(value = "UPDATE animal_board SET animal_board_favorite_count = animal_board_favorite_count + 1 WHERE animal_board_code = :boardCode",nativeQuery = true)
//    AnimalBoard plusFavCount(@Param("boardCode") int boardCode);
//
//    @Query(value = "UPDATE animal_board SET animal_board_favorite_count = animal_board_favorite_count -1 WHERE animal_board_code = :boardCode",nativeQuery = true)
//    AnimalBoard minusFavCount(@Param("boardCode") int boardCode);
}
