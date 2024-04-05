package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.AnimalBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AnimalBoardDAO extends JpaRepository<AnimalBoard, Integer>, QuerydslPredicateExecutor<AnimalBoard> {

    // 동물 별 카테고리에 따라서 게시글이 보이도록  하기
//    @Query(value = "SELECT * FROM animal_board WHERE animal_category_code =:animalCategoryCode", nativeQuery = true)

}
