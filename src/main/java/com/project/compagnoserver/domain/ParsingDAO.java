package com.project.compagnoserver.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParsingDAO extends JpaRepository<Parsing, Integer> {

    @Query(value="SELECT * FROM parsing WHERE main_cate_code= :code", nativeQuery = true)
    List<Parsing> findByMainCateCode(@Param("code") int code);

    @Query(value="SELECT * FROM parsing WHERE sub_cate_code= :code", nativeQuery = true)
    List<Parsing> findBySubCateCode(@Param("code") int code);

    @Query(value="SELECT * FROM parsing WHERE mainreg_code= :code", nativeQuery = true)
    List<Parsing> findByMainReg(@Param("code") int code);

    @Query(value="SELECT * FROM parsing WHERE main_cate_code= :code AND mainreg_code= :reg", nativeQuery = true)
    List<Parsing> findByMainCateReg(@Param("code") int code, @Param("reg") int reg);
}
