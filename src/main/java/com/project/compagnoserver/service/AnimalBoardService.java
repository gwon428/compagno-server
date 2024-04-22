package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardImage;
import com.project.compagnoserver.domain.Animal.QAnimalBoard;
import com.project.compagnoserver.domain.Animal.QAnimalCategory;
import com.project.compagnoserver.domain.user.QUser;
import com.project.compagnoserver.repo.Animal.AnimalBoardDAO;
import com.project.compagnoserver.repo.Animal.AnimalBoardImageDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AnimalBoardService {

    @Autowired
    private AnimalBoardDAO animalBoardDAO;

    @Autowired
    private AnimalBoardImageDAO animalBoardImageDAO;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QAnimalBoard qAnimalBoard = QAnimalBoard.animalBoard;
    private final QAnimalCategory qAnimalCategory = QAnimalCategory.animalCategory;
    private final QUser qUser = QUser.user;

    // 자유게시판 글쓰기 - 1) 글쓰기
    public AnimalBoard write(AnimalBoard board){
        return animalBoardDAO.save(board);
    }

    // 자유게시판 글쓰기 - 2) 이미지 저장
    public AnimalBoardImage writeImages(AnimalBoardImage images){
        return animalBoardImageDAO.save(images);
    }

    // 자유게시판 글쓰기 -3) 동영상 저장

    // 자유게시판 전체보기
    public List<AnimalBoard> viewAll(){
        return animalBoardDAO.findAll();
    }

    // 자유게시판 - 글 한개보기 = 상세페이지
    public AnimalBoard viewDetail(int animalBoardCode){

        AnimalBoard board = animalBoardDAO.findById(animalBoardCode).orElse(null);
//        log.info("board : " + board);
        return animalBoardDAO.findById(animalBoardCode).orElse(null);
    }

    // 자유게시판 - 글 수정
    public AnimalBoard boardUpdate(AnimalBoard board){
        return animalBoardDAO.existsById(board.getAnimalBoardCode()) ? animalBoardDAO.save(board) : null;
    }

    // 자유게시판 - 글 삭제

    // 자유게시판 - 조회수
    @Transactional
    public void boardView(int animalBoardCode){
        queryFactory.update(qAnimalBoard)
                .set(qAnimalBoard.animalBoardView, qAnimalBoard.animalBoardView.add(1))
                .where(qAnimalBoard.animalBoardCode.eq(animalBoardCode))
                .execute();

    }
}
