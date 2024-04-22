package com.project.compagnoserver.repo.LostBoard;

import com.project.compagnoserver.domain.LostBoard.LostBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LostBoardCommentDAO extends JpaRepository<LostBoardComment, Integer> {

    // 게시물 1개에 따른 댓글 전체 조회
    // SELECT * FROM lost_board_comment WHERE lost_board_code=1;
    @Query(value="SELECT * FROM lost_board_comment WHERE lost_board_code=:lostBoardCode", nativeQuery = true)
    List<LostBoardComment> findBylostBoardCode(@Param("lostBoardCode")int lostBoardCode);
}
