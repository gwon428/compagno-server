package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoard;
import com.project.compagnoserver.domain.NeighborBoard.QNeighborBoard;
import com.project.compagnoserver.repo.user.MyNeighborPostDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyNeighborPostService {
    @Autowired
    private MyNeighborPostDAO neighborPostDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QNeighborBoard qNeighborBoard = QNeighborBoard.neighborBoard;

    // 우리동네 게시판 게시글 리스트
    public Page<NeighborBoard> myNeighborPost(Pageable pageable, BooleanBuilder builder) {
        return neighborPostDAO.findAll(builder, pageable);
    }

    // 우리동네 게시판 게시글 갯수
    public Long CountMyNeighborPost(String id) {
        return jpaQueryFactory.select(qNeighborBoard.count())
                .from(qNeighborBoard)
                .where(qNeighborBoard.user.userId.eq(id))
                .fetchOne();
    }
}
