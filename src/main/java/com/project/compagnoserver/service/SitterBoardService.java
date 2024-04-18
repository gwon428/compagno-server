package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.SitterBoard.*;
import com.project.compagnoserver.repo.SitterBoard.SitterBoardDAO;
import com.project.compagnoserver.repo.SitterBoard.SitterBoardImageDAO;
import com.project.compagnoserver.repo.SitterBoard.SitterCommentDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitterBoardService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private SitterBoardDAO sitterBoardDAO;
    private final QSitterBoard qSitterBoard = QSitterBoard.sitterBoard;

    @Autowired
    private SitterBoardImageDAO sitterBoardImageDAO;
    private final QSitterBoardImage qSitterBoardImage = QSitterBoardImage.sitterBoardImage;

    @Autowired
    private SitterCommentDAO sitterCommentDAO;
    private final QSitterBoardComment qSitterBoardComment = QSitterBoardComment.sitterBoardComment;


    // 전체 보기
    public List<SitterBoard> sitterViewAll() {
        return sitterBoardDAO.findAll();
    }


    // 상세 보기
    public SitterBoard sitterView(int code) {
        return sitterBoardDAO.findById(code).orElse(null);
    }
    public List<SitterBoardImage> sitterViewImg(int code) {
        return queryFactory.selectFrom(qSitterBoardImage)
                .where(qSitterBoardImage.sitterBoard.sitterBoardCode.eq(code))
                .fetch();
    }


    // 글 등록
    public SitterBoard sitterCreate(SitterBoard sitter) {
        return sitterBoardDAO.save(sitter);
    }
    public void sitterCreateImg(SitterBoardImage sitterImg) {
        sitterBoardImageDAO.save(sitterImg);
    }

    // 글 수정
    public void sitterUpdate(SitterBoard sitter) {
        if(sitterBoardDAO.existsById(sitter.getSitterBoardCode())) {
            sitterBoardDAO.save(sitter);
        }
    }
    public void sitterUpdateImg(SitterBoardImage sitterImg) {
        sitterDeleteImg(sitterImg.getSitterBoard().getSitterBoardCode());
        sitterCreateImg(sitterImg);
    }

    // 글 삭제
    public void sitterDelete(int code) {
        SitterBoard target = sitterBoardDAO.findById(code).orElse(null);
        if(target != null) {
            sitterBoardDAO.delete(target);
        }
    }
    public void sitterDeleteImg(int code) {
        queryFactory.delete(qSitterBoardImage)
                .where(qSitterBoardImage.sitterBoard.sitterBoardCode.eq(code))
                .execute();
    }


    // 댓글 추가
    public SitterBoardComment sitterCreateComment(SitterBoardComment sitterBoardComment) {
        return sitterCommentDAO.save(sitterBoardComment);
    }

    // 댓글 삭제
    public void sitterCommentDelete(int commentCode) {
        SitterBoardComment target = sitterCommentDAO.findById(commentCode).orElse(null);
        if(target != null) {
            sitterCommentDAO.delete(target);
        }
    }

    // 원 댓글만 조회
    public List<SitterBoardComment> getTopComments(int code) {
        return queryFactory.selectFrom(qSitterBoardComment)
                .where(qSitterBoardComment.sitterCommentParentCode.eq(0))
                .where(qSitterBoardComment.sitterBoardCode.eq(code))
                .orderBy(qSitterBoardComment.sitterCommentCode.desc())
                .fetch();
    }

    // 대댓글만 조회
    public List<SitterBoardComment> getReplyComments(int parent, int code) {
        return queryFactory.selectFrom(qSitterBoardComment)
                .where(qSitterBoardComment.sitterCommentParentCode.eq(parent))
                .where(qSitterBoardComment.sitterBoardCode.eq(code))
                .orderBy(qSitterBoardComment.sitterCommentRegiDate.asc())
                .fetch();
    }



}
