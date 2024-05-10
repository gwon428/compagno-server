package com.project.compagnoserver.repo.Note;

import com.project.compagnoserver.domain.Note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoteDAO extends JpaRepository<Note, Integer>, QuerydslPredicateExecutor<Note> {

//@Query(value="SELECT * FROM note WHERE sender=:nickname or receiver=:nickname", nativeQuery = true)
//Page<Note> findByNickname(@Param("nickname")String nickname, BooleanBuilder builder, Pageable pageable);

}
