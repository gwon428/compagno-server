package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.NeighborBoard.NeighborBoard;
import com.project.compagnoserver.domain.NeighborBoard.QNeighborBoard;
import com.project.compagnoserver.domain.NeighborBoard.QNeighborBoardBookmark;
import com.project.compagnoserver.domain.NeighborBoard.QNeighborBoardImage;
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
}
