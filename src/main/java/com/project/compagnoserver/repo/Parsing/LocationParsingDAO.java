package com.project.compagnoserver.repo.Parsing;

import com.project.compagnoserver.domain.Parsing.LocationParsing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationParsingDAO extends JpaRepository<LocationParsing, Integer> {
    // 시도에 따른 시군구 전체 조회
    @Query(value = "SELECT * FROM location WHERE location_parent_code = :code", nativeQuery = true)
    List<LocationParsing> findByParentCode(@Param("code") int code);
}
