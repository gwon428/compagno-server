package com.project.compagnoserver.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParsingDAO extends JpaRepository<Parsing, Integer> {

    @Query(value="SELECT * FROM parsing WHERE main_cate_code= :code", nativeQuery = true)
    List<Parsing> findByMainCateCode(@Param("code") int code);
}
