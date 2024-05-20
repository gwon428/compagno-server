package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.*;
import com.project.compagnoserver.domain.user.QUser;
import com.project.compagnoserver.repo.Animal.AnimalBoardDAO;
import com.project.compagnoserver.repo.Animal.AnimalBoardImageDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final QAnimalBoardImage qAnimalBoardImage = QAnimalBoardImage.animalBoardImage1;
    private final QAnimalBoardFavorite qAnimalBoardFavorite = QAnimalBoardFavorite.animalBoardFavorite;

    private final QAnimalBoardComment qAnimalBoardComment = QAnimalBoardComment.animalBoardComment;

    // 자유게시판 글쓰기 - 1) 글쓰기
    public AnimalBoard write(AnimalBoard board){
        return animalBoardDAO.save(board);
    }

    // 자유게시판 글쓰기 - 2) 이미지 저장
    public AnimalBoardImage saveImages(AnimalBoardImage images){
        return animalBoardImageDAO.save(images);
    }

    // 자유게시판 글쓰기 - 2-2) 완성된 이미지 리스트 불러오기
    public AnimalBoardImage getThumbnailList(int boardCode){
//        log.info("받은 boardCode : " + boardCode);
        return queryFactory.selectFrom(qAnimalBoardImage)
                .where(qAnimalBoardImage.animalBoard.animalBoardCode.eq(boardCode))
                .fetchFirst();
    }

    // 자유게시판 글쓰기 - 2-3) 썸네일 저장
    @Transactional
    public void saveThumbnail(String image, AnimalBoard vo){
//        log.info("service thumbnail : " + image);
         queryFactory.update(qAnimalBoard)
                .set(qAnimalBoard.animalMainImage, image)
                .where(qAnimalBoard.animalBoardCode.eq(vo.getAnimalBoardCode()))
                .execute();
    }

        // 자유게시판 글쓰기 - 3) 이미지 불러오기
        public List<AnimalBoardImage> viewImages(){
            return queryFactory.selectFrom(qAnimalBoardImage)
                    .where(qAnimalBoardImage.animalBoard.animalBoardCode.isNull())
                    .fetch();
            // 맨 처음 이미지 삽입시 boardCode가 생성된 것이 아니므로 이미지가 우선적으로 먼저 DB에 들어감,
            // 그리고 그 이미지들을 불러오는 것 -유저가 여럿이서 이 사건이 일어나면 추후에 image에 user정보도 같이 넣어두면됨
        }
        // 추후에 null로 남은 찌꺼기 파일들 정리
        @Transactional
        public void deleteAnimalNoImages(){
            queryFactory.delete(qAnimalBoardImage)
                    .where(qAnimalBoardImage.animalBoard.animalBoardCode.isNull())
                    .execute();
        }
        @Transactional
        public void deleteAnimalNoContent(){
            queryFactory.delete(qAnimalBoardImage)
                    .where(qAnimalBoardImage.animalBoard.animalBoardCode.isNull())
                    .execute();
        }

         // 자유게시판 전체보기
         public Page<AnimalBoard> viewAll(Pageable pageable, BooleanBuilder builder){
//            Page<AnimalBoard> board =
                    return animalBoardDAO.findAll(builder, pageable);
//             JPAQuery<Long> countQuery = queryFactory.select(qAnimalBoardComment.count()).from(qAnimalBoardComment)
//                     .where(qAnimalBoardComment.animalBoard.animalBoardCode.eq());




    }

        // 자유게시판 - 좋아요 상위권 전체보기
        public List<AnimalBoard> viewRankers(){
            return queryFactory.selectFrom(qAnimalBoard)
                    .orderBy(qAnimalBoard.animalBoardFavoriteCount.desc())
                    .fetch();

        }
    // 자유게시판 - 좋아요 상위권 전체보기 - favorite board로 가져오기
        public List<AnimalBoardFavorite> favList (){
            return queryFactory.selectFrom(qAnimalBoardFavorite)
//                    .where(qAnimalBoardFavorite.animalBoard.animalBoardCode.eq())
                    .fetch();
        }


        // 자유게시판 카테고리 불러오기
        public List<AnimalCategory> viewCategory(){
            return queryFactory.selectFrom(qAnimalCategory).fetch();
        }

        // 자유게시판 - 글 한개보기 = 상세페이지
        public AnimalBoard viewDetail(int animalBoardCode){

            AnimalBoard board = animalBoardDAO.findById(animalBoardCode).orElse(null);
//        log.info("board : " + board);
            return animalBoardDAO.findById(animalBoardCode).orElse(null);
        }
        // 자유게시판 - 글 수정 - 기존 이미지 가져오기
        public List<AnimalBoardImage> getPrevImages(int boardCode){
//            return animalBoardImageDAO.prevImages(boardCode);
            return queryFactory.selectFrom(qAnimalBoardImage)
                    .where(qAnimalBoardImage.animalBoard.animalBoardCode.eq(boardCode))
                    .fetch();
        }
        // 자유게시판 - 글 수정
        public AnimalBoard boardUpdate(AnimalBoard board){
            return animalBoardDAO.existsById(board.getAnimalBoardCode()) ? animalBoardDAO.save(board) : null;
        }

        // 자유게시판 - 글 삭제
        @Transactional
        public void deleteBoard(int boardCode){
            animalBoardDAO.deleteById(boardCode);
        }
        // 자유게시판 - 글 삭제 2, 현재 이미지 불러오기
        public List<AnimalBoardImage> getCurrentFiles(int boardCode){
        return queryFactory.selectFrom(qAnimalBoardImage)
                .where(qAnimalBoard.animalBoardCode.eq(boardCode))
                .fetch();
        }


        // 자유게시판 - 조회수
        @Transactional
        public void boardView(int animalBoardCode){
            queryFactory.update(qAnimalBoard)
                    .set(qAnimalBoard.animalBoardView, qAnimalBoard.animalBoardView.add(1))
                    .where(qAnimalBoard.animalBoardCode.eq(animalBoardCode))
                    .execute();

        }
    }
