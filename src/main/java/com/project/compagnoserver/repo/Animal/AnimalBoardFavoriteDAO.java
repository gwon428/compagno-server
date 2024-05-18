package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.ResultSet;

public interface AnimalBoardFavoriteDAO extends JpaRepository<AnimalBoardFavorite,Integer> {
    @Query(value = "SELECT * FROM animal_board_favorite WHERE animal_board_code = :boardCode AND user_id = :userId",nativeQuery = true)
    AnimalBoardFavorite checkFavorite(@Param("boardCode") int boardCode, @Param("userId") String userId);

    //
    @Query(value = "SELECT\n" +
            "COUNT(*) as animal_favorite_count\n" +
            "FROM animal_board_favorite\n" +
            "where animal_board_code=:code AND animal_favorite_date >= DATE_FORMAT(now(), '%Y-%m-%d %H:00:00')\n" +
            "group by DATE_FORMAT(animal_favorite_date, '%Y-%m-%d %H:00:00')", nativeQuery = true)
    Integer latestFavCount(@Param("code") int boardCode);
}
