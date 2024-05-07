package com.project.compagnoserver.repo.AdoptionBoard;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface AdoptionBoardImageDAO extends JpaRepository<AdoptionBoardImage, Integer> {

    @Query(value = "SELECT * FROM adoption_board_image WHERE adoption_board_code = :adopBoardCode", nativeQuery = true)
    List<AdoptionBoardImage> findByCode(@Param("adopBoardCode")Integer adopBoardCode);
}
