package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.SitterBoard.QSitterBoard;
import com.project.compagnoserver.domain.SitterBoard.SitterBoard;
import com.project.compagnoserver.repo.user.MySitterDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MySitterService {
    @Autowired
    private MySitterDAO sitterDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QSitterBoard qSitterBoard = QSitterBoard.sitterBoard;

    // 내 시터 공고 리스트 출력
    public Page<SitterBoard> mySitterList(Pageable pageable, BooleanBuilder builder) {
        return sitterDAO.findAll(builder, pageable);
    }

    // 내 시터 공고 갯수 출력
    public Long countMySitter(String id) {
        return jpaQueryFactory.select(qSitterBoard.count())
                .from(qSitterBoard)
                .where(qSitterBoard.user.userId.eq(id))
                .fetchOne();
    }
}
