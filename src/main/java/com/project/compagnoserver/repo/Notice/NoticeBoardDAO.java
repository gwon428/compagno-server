package com.project.compagnoserver.repo.Notice;

import com.project.compagnoserver.domain.NoticeBoard.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardDAO extends JpaRepository<NoticeBoard, Integer> {
}
