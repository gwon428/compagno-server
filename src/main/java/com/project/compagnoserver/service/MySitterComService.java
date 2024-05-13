package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.SitterBoard.QSitterBoardComment;
import com.project.compagnoserver.domain.SitterBoard.SitterBoardComment;
import com.project.compagnoserver.repo.user.MySitterComDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MySitterComService {

    @Autowired
    private MySitterComDAO sitterComDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QSitterBoardComment qSitterBoardComment = QSitterBoardComment.sitterBoardComment;

    // 내 시터게시판 댓글 리스트
    public Page<SitterBoardComment> mySitterComList(Pageable pageable, BooleanBuilder builder) {
        return sitterComDAO.findAll(builder, pageable);
    }

    // 내 시터게시판 댓글 갯수
    public Long countMySitterCom(String id) {
        return jpaQueryFactory.select(qSitterBoardComment.count())
                .from(qSitterBoardComment)
                .where(qSitterBoardComment.user.userId.eq(id))
                .fetchOne();
    }

}
