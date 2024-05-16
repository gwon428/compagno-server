package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.UserQnaBoard.QUserQnaQuestionBoard;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoard;
import com.project.compagnoserver.repo.user.MyUserQnaDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyUserQnaService {
    @Autowired
    private MyUserQnaDAO userQnaDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QUserQnaQuestionBoard qUserQnaQuestionBoard = QUserQnaQuestionBoard.userQnaQuestionBoard;

    // 내 유저-유저 질문 리스트 출력
    public Page<UserQnaQuestionBoard> myUserQnaList(Pageable pageable, BooleanBuilder builder) {
        return userQnaDAO.findAll(builder, pageable);
    }

    // 내 유저-유저 질문 갯수 출력
    public Long countMyUserQna(String id) {
        return jpaQueryFactory.select(qUserQnaQuestionBoard.count())
                .from(qUserQnaQuestionBoard)
                .where(qUserQnaQuestionBoard.userId.eq(id))
                .fetchOne();
    }
}
