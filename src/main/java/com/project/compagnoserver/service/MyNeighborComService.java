package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardComment;
import com.project.compagnoserver.domain.NeighborBoard.QNeighborBoardComment;
import com.project.compagnoserver.repo.user.MyNeighborComDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyNeighborComService {
    @Autowired
    private MyNeighborComDAO neighborComDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QNeighborBoardComment qNeighborBoardComment = QNeighborBoardComment.neighborBoardComment;

    // 우리동네 게시판 댓글 리스트
    public Page<NeighborBoardComment> myNeighborCom(Pageable pageable, BooleanBuilder builder) {
        return neighborComDAO.findAll(builder, pageable);
    }

    // 우리동네 게시판 댓글 갯수
    public Long countmyNeighborCom(String id) {
        return jpaQueryFactory.select(qNeighborBoardComment.count())
                .from(qNeighborBoardComment)
                .where(qNeighborBoardComment.user.userId.eq(id))
                .fetchOne();
    }
}
