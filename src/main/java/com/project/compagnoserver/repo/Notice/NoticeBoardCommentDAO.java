package com.project.compagnoserver.repo.Notice;

import com.project.compagnoserver.domain.NoticeBoard.NoticeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardCommentDAO  extends JpaRepository<NoticeBoardComment, Integer> {
}
