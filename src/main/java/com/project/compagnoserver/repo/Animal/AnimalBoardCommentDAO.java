package com.project.compagnoserver.repo.Animal;

import com.project.compagnoserver.domain.Animal.AnimalBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimalBoardCommentDAO extends JpaRepository<AnimalBoardComment, Integer> {

    // 부모 댓글 삭제 - 사실상 deleteById를 사용하므로 의미 없음
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM animal_board_comment WHERE animal_comment_code = :commentCode", nativeQuery = true)
    void deleteParentComment(@Param("commentCode") Integer commentCode);

    // 자식 댓글 삭제 - 만약 부모댓글 삭제가 이루어진다면 여기서 받는 값은 자식의 댓글코드가 아닌, 부모의 댓글 코드
    // 자식 댓글 삭제만 일어나면 여기서 받는 값은 자식이 댓글 코드
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM animal_board_comment WHERE animal_parent_code= :commentCode", nativeQuery = true)
    void  deleteChildrenComment(@Param("commentCode") Integer commentCode);
}
