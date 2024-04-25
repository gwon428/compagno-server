package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.NeighborBoard.*;
import com.project.compagnoserver.repo.NeighborBoard.NeighborBoardBookmarkDAO;
import com.project.compagnoserver.repo.NeighborBoard.NeighborBoardDAO;
import com.project.compagnoserver.repo.NeighborBoard.NeighborBoardImageDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NeighborBoardService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private NeighborBoardDAO neighborBoardDAO;
    private final QNeighborBoard qNeighborBoard = QNeighborBoard.neighborBoard;

    @Autowired
    private NeighborBoardImageDAO neighborBoardImageDAO;
    private final QNeighborBoardImage qNeighborBoardImage = QNeighborBoardImage.neighborBoardImage;

    @Autowired
    private NeighborBoardBookmarkDAO neighborBoardBookmarkDAO;
    private final QNeighborBoardBookmark qNeighborBoardBookmark = QNeighborBoardBookmark.neighborBoardBookmark;


    // 전체 보기
    public List<NeighborBoard> neighborViewAll() {
        return neighborBoardDAO.findAll();
    }
    public List<NeighborBoardImage> neighborViewAllImg(int code) {
        return neighborBoardImageDAO.findByBoardCode(code);
    }

    // 상세 페이지
    public NeighborBoard neighborView(int code) {
        return neighborBoardDAO.findById(code).orElse(null);
    }
    public List<NeighborBoardImage> neighborViewImg(int code) {
        return queryFactory.selectFrom(qNeighborBoardImage)
                .where(qNeighborBoardImage.neighborBoard.neighborBoardCode.eq(code))
                .fetch();
    }


    // 게시글 등록
    public NeighborBoard neighborCreate(NeighborBoard neighborBoardVo) {
        return neighborBoardDAO.save(neighborBoardVo);
    }
    public void neighborCreateImg(NeighborBoardImage neighborBoardImageVo) {
        neighborBoardImageDAO.save(neighborBoardImageVo);
    }


    // 게시글 삭제
    public void neighborDeleteImg(int code) {
        if(neighborBoardImageDAO.existsById(code)) {
            neighborBoardImageDAO.deleteById(code);
        }
    }
    public void neighborDelete(int code) {
        if(neighborBoardDAO.existsById(code)) {
            neighborBoardDAO.deleteById(code);
        }
    }


}
