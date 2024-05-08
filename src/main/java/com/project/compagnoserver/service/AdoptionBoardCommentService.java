package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoardComment;
import com.project.compagnoserver.domain.AdoptionBoard.QAdoptionBoardComment;
import com.project.compagnoserver.domain.LostBoard.LostBoardComment;
import com.project.compagnoserver.repo.AdoptionBoard.AdoptionBoardCommentDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdoptionBoardCommentService {

    @Autowired
    private AdoptionBoardCommentDAO dao;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QAdoptionBoardComment qAdoptionBoardComment = QAdoptionBoardComment.adoptionBoardComment;

    // 댓글 추가
    public AdoptionBoardComment create(AdoptionBoardComment vo) {return dao.save(vo);}

    // 페이징 없이 상위 댓글 조회
    public List<AdoptionBoardComment> topAllComments(int adopBoardCode){
        return queryFactory
                .selectFrom(qAdoptionBoardComment)
                .where(qAdoptionBoardComment.adopParentCode.eq(0))
                .where(qAdoptionBoardComment.adopBoardCode.eq(adopBoardCode))
                .orderBy(qAdoptionBoardComment.commentDate.desc())
                .fetch();
    }

    // 상위 댓글 조회
    public List<AdoptionBoardComment> topComments(int adopBoardCode, int page){
        return queryFactory
                .selectFrom(qAdoptionBoardComment)
                .where(qAdoptionBoardComment.adopParentCode.eq(0))
                .where(qAdoptionBoardComment.adopBoardCode.eq(adopBoardCode))
                .orderBy(qAdoptionBoardComment.commentDate.desc())
                .offset(5*(page-1))
                .limit(5)
                .fetch();
    }


    // 하위 댓글 조회
    public List<AdoptionBoardComment> bottomComments(int parent, int adopBoardCode){
        return queryFactory
                .selectFrom(qAdoptionBoardComment)
                .where(qAdoptionBoardComment.adopParentCode.eq(parent))
                .where(qAdoptionBoardComment.adopBoardCode.eq(adopBoardCode))
                .orderBy(qAdoptionBoardComment.commentDate.desc())
                .fetch();
    }

    // 댓글 수정
    public AdoptionBoardComment update(AdoptionBoardComment vo){
        AdoptionBoardComment comment = dao.findById(vo.getAdopCommentCode()).orElse(null);
        if(comment!=null){
            return dao.save(vo);
        }
        return null;
    }

    // 댓글 삭제
    public void deleteComment(int adopCommentCode){
        AdoptionBoardComment vo = dao.findById(adopCommentCode).orElse(null);
        if(vo!=null){
            dao.delete(vo);
        }
    }

    // adopCommentcode로 댓글 찾기
    public AdoptionBoardComment viewComment(int adopCommentCode){
        return dao.findById(adopCommentCode).orElse(null);
    }
}
