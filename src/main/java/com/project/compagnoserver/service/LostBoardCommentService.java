package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.LostBoard.LostBoardComment;
import com.project.compagnoserver.domain.LostBoard.QLostBoardComment;
import com.project.compagnoserver.repo.LostBoard.LostBoardCommentDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LostBoardCommentService {

    @Autowired
    private LostBoardCommentDAO dao;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QLostBoardComment qLostBoardComment = QLostBoardComment.lostBoardComment;

    // 댓글 추가
    public LostBoardComment create(LostBoardComment vo){return dao.save(vo);}

    // 댓글 조회
    // 상위 댓글 조회
    /*
    SELECT * FROM lost_board_comment
    WHERE lost_parent_code=0   부모 코드가 0 : 부모가 없음(즉, 가장 상위 댓글이라는 의미)
    AND lost_board_code=1      게시물 코드 번호
    ORDER BY comment_date DESC;
     */
    public List<LostBoardComment> topComments(int lostBoardCode, int page){
        return queryFactory
                .selectFrom(qLostBoardComment)
                .where(qLostBoardComment.lostParentCode.eq(0))
                .where(qLostBoardComment.lostBoardCode.eq(lostBoardCode))
                .orderBy(qLostBoardComment.commentDate.desc())
                .offset(5*(page-1))
                .limit(5)
                .fetch();
    }

    public List<LostBoardComment> topAllComments(int lostBoardCode){
        return queryFactory
                .selectFrom(qLostBoardComment)
                .where(qLostBoardComment.lostParentCode.eq(0))
                .where(qLostBoardComment.lostBoardCode.eq(lostBoardCode))
                .orderBy(qLostBoardComment.commentDate.desc())
                .fetch();
    }

    // 하위 댓글 조회
    public List<LostBoardComment> bottomComments(int parent, int lostBoardCode){
        return queryFactory
                .selectFrom(qLostBoardComment)
                .where(qLostBoardComment.lostParentCode.eq(parent))
                .where(qLostBoardComment.lostBoardCode.eq(lostBoardCode))
                .orderBy(qLostBoardComment.commentDate.asc())
                .fetch();
    }

    // 댓글 수정
    public LostBoardComment update(LostBoardComment vo){
        LostBoardComment comment = dao.findById(vo.getLostCommentCode()).orElse(null);
        if(comment!=null){
            return dao.save(vo);
        }
        return null;
    }


    // 댓글 삭제
    public void deleteComment(int lostCommentCode){
        LostBoardComment vo = dao.findById(lostCommentCode).orElse(null);
        if(vo!=null){
            dao.delete(vo);
        }
    }

    // lostCommentCode로 댓글 찾기
    public LostBoardComment viewComment(int lostCommentCode){
        return dao.findById(lostCommentCode).orElse(null);
    }
}
