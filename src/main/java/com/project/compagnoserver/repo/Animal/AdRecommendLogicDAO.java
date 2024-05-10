package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AdRecommendLogic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AdRecommendLogicDAO extends JpaRepository<AdRecommendLogic, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO ad_recommend_logic(user_id, category_code) VALUES(:id,:code)", nativeQuery = true)
    void setLogicBase(@Param("id")String id, @Param("code") int code);
}
