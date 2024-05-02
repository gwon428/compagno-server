package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import com.project.compagnoserver.repo.user.MyActivityDAO;
import com.querydsl.core.BooleanBuilder;
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

    // 내 좋아요 목록 출력
    public Page<AnimalBoardFavorite> myFavList(Pageable pageable, BooleanBuilder builder) {
        return maDAO.findAll(builder, pageable);
    }

}
