package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoteDAO extends JpaRepository<Note, Integer>, QuerydslPredicateExecutor<Note> {
}
