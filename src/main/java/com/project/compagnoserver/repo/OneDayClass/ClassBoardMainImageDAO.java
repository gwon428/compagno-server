package com.project.compagnoserver.repo.OneDayClass;

import com.project.compagnoserver.domain.LostBoard.LostBoardImage;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardMainImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassBoardMainImageDAO extends JpaRepository<ClassBoardMainImage, Integer> {

    @Query(value="SELECT * FROM oneday_class_board_main_image WHERE odc_code = :code", nativeQuery = true)
    List<ClassBoardMainImage> findByCode(@Param("code")Integer code);
    // 어노테이션 쿼리로 odcMImg 테이블에 코드로 조건을 걸어서 찾기 !!
}
