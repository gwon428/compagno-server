package com.project.compagnoserver.repo.Notice;

import com.project.compagnoserver.domain.NoticeBoard.NoticeBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardImageDAO extends JpaRepository<NoticeBoardImage, Integer> {
}
