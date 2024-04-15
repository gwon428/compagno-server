package com.project.compagnoserver.repo.Note;

import com.project.compagnoserver.domain.Note.NoteFIle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteFileDAO extends JpaRepository<NoteFIle, Integer> {
}
