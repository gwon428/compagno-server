package com.project.compagnoserver.repo.RegisterPet;

import com.project.compagnoserver.domain.RegisterPet.RegisterPetLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegisterPetLocationDAO  extends JpaRepository<RegisterPetLocation, Integer> {
    // 시도에 따른 시군구 전체 조회
    @Query(value = "SELECT * FROM location WHERE location_parent_code = :code", nativeQuery = true)
    List<RegisterPetLocation> findByParentCode(@Param("code") int code);
}
