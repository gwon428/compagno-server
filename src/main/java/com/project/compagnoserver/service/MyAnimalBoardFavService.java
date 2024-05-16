package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import com.project.compagnoserver.domain.Animal.QAnimalBoard;
import com.project.compagnoserver.domain.Animal.QAnimalBoardFavorite;
import com.project.compagnoserver.repo.user.MyAnimalBoardFavDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MyAnimalBoardFavService {

    @Autowired
    private MyAnimalBoardFavDAO mabfDAO;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QAnimalBoardFavorite qAnimalBoardFavorite = QAnimalBoardFavorite.animalBoardFavorite;

    private  final QAnimalBoard qAnimalBoard = QAnimalBoard.animalBoard;
    // 내 좋아요 목록 출력
    public Page<AnimalBoardFavorite> myFavList(Pageable pageable, BooleanBuilder builder) {
        return mabfDAO.findAll(builder, pageable);
    }

    // 내 좋아요 갯수 출력
    public Long countFav(String id) {
        return queryFactory.select(qAnimalBoardFavorite.count())
                .from(qAnimalBoardFavorite)
                .where(qAnimalBoardFavorite.user.userId.eq(id))
                .fetchOne();

    }

    // 좋아요 삭제
    public void deleteFav(String id, int code) {
        queryFactory.delete(qAnimalBoardFavorite)
                .where(qAnimalBoardFavorite.user.userId.eq(id))
                .where(qAnimalBoardFavorite.animalFavoriteCode.eq(code))
                .execute();

    }

    // 좋아요 삭제 시 숫자 마이너스 처리
    public void updateFavCount(int code) {
        queryFactory.update(qAnimalBoard)
                .set(qAnimalBoard.animalBoardFavoriteCount, qAnimalBoard.animalBoardFavoriteCount.add(-1))
                .where(qAnimalBoard.animalBoardCode.eq(code))
                .execute();
    }
}
