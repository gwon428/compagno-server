package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.domain.OneDayClass.QClassBoard;
import com.project.compagnoserver.repo.user.MyOdcDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyOdcService {
    @Autowired
    private MyOdcDAO odcDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QClassBoard qClassBoard = QClassBoard.classBoard;

    // 내 원데이클래스 리스트 출력
    public Page<ClassBoard> myOdcList(Pageable pageable, BooleanBuilder builder) {
        return odcDAO.findAll(builder, pageable);
    }

    // 내 원데이클래스 갯수 출력
    public Long countMyOdc(String id) {
        return jpaQueryFactory.select(qClassBoard.count())
                .from(qClassBoard)
                .where(qClassBoard.user.userId.eq(id))
                .fetchOne();
    }
}
