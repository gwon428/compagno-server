package com.project.compagnoserver.repo.Parsing;

import com.project.compagnoserver.domain.Parsing.ContentsLocationParsing;
import com.project.compagnoserver.domain.Parsing.ContentsLocationParsingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsLocationParsingDAO extends JpaRepository<ContentsLocationParsing, Integer> {
    @Query(value= "SELECT * FROM location WHERE location_parent_code = :code", nativeQuery = true)
    List<ContentsLocationParsing> findByParentCode(@Param("code") int code);
}
