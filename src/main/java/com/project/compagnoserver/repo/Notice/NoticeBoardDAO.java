package com.project.compagnoserver.repo.Notice;

import com.project.compagnoserver.domain.NoticeBoard.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoticeBoardDAO extends JpaRepository<NoticeBoard, Integer>, QuerydslPredicateExecutor<NoticeBoard> {
}
