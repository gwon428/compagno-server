package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface NoteDAO extends JpaRepository<Note, Integer>, QuerydslPredicateExecutor<Note> {

    //@Query(value="SELECT * FROM product WHERE cate_code=:code", nativeQuery = true)
    @Query(value="SELECT * FROM note WHERE sender LIKE '%:sender%' OR receiver LIKE '%:receiver%' OR note_title LIKE '%:noteTitle%' OR note_regi_Date = :noteRegiDate", nativeQuery=true)
    Page<Note> findBySearch(@Param("sender") String sender, @Param("receiver")String receiver, @Param("noteTitle")String noteTitle, @Param("noteRegiDate") Date noteRegiDate, Pageable pageable);

}
