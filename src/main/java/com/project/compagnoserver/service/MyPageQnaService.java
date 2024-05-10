package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.QnaQ.QQnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.repo.user.MyPageQnaDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageQnaService {

    @Autowired
    private MyPageQnaDAO myQnaDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QQnaQBoard qQnaQBoard = QQnaQBoard.qnaQBoard;

    // 일반유저 - 내가 작성한 질문 리스트
    public Page<QnaQBoard> userQnaList(Pageable pageable, BooleanBuilder builder) {
        return myQnaDAO.findAll(builder, pageable);

    }

    // 일반유저 - 페이징 위해 내가 작성한 질문 갯수
    public Long countQna(String id) {
            return jpaQueryFactory.select(qQnaQBoard.count())
                    .from(qQnaQBoard)
                    .where(qQnaQBoard.userId.eq(id))
                    .fetchOne();
    }

    // 관리자 - 미답변 질문 리스트
    public Page<QnaQBoard> managerQnaList(Pageable pageable, BooleanBuilder builder) {
        return myQnaDAO.findAll(builder, pageable);
    }

    // 관리자 - 미답변 질문 갯수
    public Long countmanagerQna() {
        return jpaQueryFactory.select(qQnaQBoard.count())
                .from(qQnaQBoard)
                .where(qQnaQBoard.qnaQStatus.eq("N"))
                .fetchOne();
    }
}
