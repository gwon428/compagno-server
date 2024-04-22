package com.project.compagnoserver.repo.LostBoard;

import com.project.compagnoserver.domain.LostBoard.LostBoardImage;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LostBoardImageDAO extends JpaRepository<LostBoardImage, Integer> {

    @Query(value="SELECT * FROM lost_board_image WHERE lost_board_code = :lostBoardCode", nativeQuery = true)
    List<LostBoardImage> findByCode(@Param("lostBoardCode")Integer lostBoardCode);


}
