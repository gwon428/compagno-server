package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoard;
import com.project.compagnoserver.domain.AdoptionBoard.QAdoptionBoard;
import com.project.compagnoserver.repo.user.MyAdoptionDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyAdoptionService {

    @Autowired
    private MyAdoptionDAO adopDAO;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QAdoptionBoard qAdoptionBoard = QAdoptionBoard.adoptionBoard;

    // 내 입양공고 리스트 출력
    public Page<AdoptionBoard> myAdopList(Pageable pageable, BooleanBuilder builder) {
        return adopDAO.findAll(builder, pageable);
    }

    // 내 입양공고 갯수 불러오기
    public Long countMyAdoption(String id) {
        return queryFactory.select(qAdoptionBoard.count())
                .from(qAdoptionBoard)
                .where(qAdoptionBoard.userId.eq(id))
                .fetchOne();
    }
}
