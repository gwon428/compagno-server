package com.project.compagnoserver.repo.Note;

import com.project.compagnoserver.domain.Note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoteDAO extends JpaRepository<Note, Integer>, QuerydslPredicateExecutor<Note> {

@Modifying
    @Transactional
    @Query(value="UPDATE note SET star_sender=(!star_sender) WHERE note_code =:noteCode",nativeQuery = true )
    void updateStarSender(@Param("noteCode")int noteCode);

    @Modifying
    @Transactional
    @Query(value="UPDATE note SET star_receiver=(!star_receiver) WHERE note_code =:noteCode",nativeQuery = true )
    void updateStarReceiver(@Param("noteCode")int noteCode);

//    @Query(value="SELECT * FROM note WHERE sender=:nickName OR receiver=:nickName", nativeQuery = true)
//    List<Note> viewAllNotPage(@Param("nickName")String nickName);

}
