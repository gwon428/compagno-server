package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.LostBoard.LostBoard;
import com.project.compagnoserver.domain.LostBoard.QLostBoard;
import com.project.compagnoserver.repo.user.MyLostDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyLostService {

    @Autowired
    private MyLostDAO lostDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QLostBoard qLostBoard = QLostBoard.lostBoard;

    // 내 실종동물 리스트 출력
    public Page<LostBoard> myLostList(Pageable pageable, BooleanBuilder builder) {
        return lostDAO.findAll(builder, pageable);
    }

    // 내 실종동물 갯수 불러오기
    public Long countMyLost(String id) {
        return jpaQueryFactory.select(qLostBoard.count())
                .from(qLostBoard)
                .where(qLostBoard.userId.eq(id))
                .fetchOne();
    }


}
