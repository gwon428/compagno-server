package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.NoteFIle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteFileDAO extends JpaRepository<NoteFIle, Integer> {
}
