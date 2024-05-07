package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import com.project.compagnoserver.domain.Animal.QAnimalBoardFavorite;
import com.project.compagnoserver.repo.user.MyActivityDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class MyActivityService {

    @Autowired
    private MyActivityDAO maDAO;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QAnimalBoardFavorite qAnimalBoardFavorite = QAnimalBoardFavorite.animalBoardFavorite;

    // 내 좋아요 목록 출력
    public Page<AnimalBoardFavorite> myFavList(Pageable pageable, BooleanBuilder builder) {
        return maDAO.findAll(builder, pageable);
    }

    // 내 좋아요 갯수 출력
    public Long countFav(String id) {

        return queryFactory.select(qAnimalBoardFavorite.count())
                .from(qAnimalBoardFavorite)
                .where(qAnimalBoardFavorite.user.userId.eq(id))
                .fetchOne();

    }

}
