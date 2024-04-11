package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.Note;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NoteDAO extends JpaRepository<Note, Integer>, QuerydslPredicateExecutor<Note> {


}
