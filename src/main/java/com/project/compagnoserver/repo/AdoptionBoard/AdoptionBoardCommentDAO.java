package com.project.compagnoserver.repo.AdoptionBoard;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoardComment;
import com.project.compagnoserver.domain.LostBoard.LostBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdoptionBoardCommentDAO extends JpaRepository<AdoptionBoardComment, Integer> {

    // 게시물 1개에 따른 댓글 전체 조회
    @Query(value="SELECT * FROM adoption_board_comment WHERE adoption_board_code=:adopBoardCode", nativeQuery = true)
    List<AdoptionBoardComment> findByadopBoardCode(@Param("adopBoardCode")int adopBoardCode);
}
