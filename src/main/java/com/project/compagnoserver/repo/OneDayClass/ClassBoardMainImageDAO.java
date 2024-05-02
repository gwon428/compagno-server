package com.project.compagnoserver.repo.OneDayClass;

import com.project.compagnoserver.domain.LostBoard.LostBoardImage;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardMainImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassBoardMainImageDAO extends JpaRepository<ClassBoardMainImage, Integer> {

    @Query(value="SELECT * FROM oneday_class_board_main_image WHERE odc_code = :odcCode", nativeQuery = true)
    List<ClassBoardMainImage> findByCode(@Param("odcCode")Integer odcCode);

}
