package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.QAnimalCategory;
import com.project.compagnoserver.domain.ProductBoard.ProductBoardBookmark;
import com.project.compagnoserver.domain.ProductBoard.QProductBoard;
import com.project.compagnoserver.domain.ProductBoard.QProductBoardBookmark;
import com.project.compagnoserver.repo.user.MyProductBoardFavDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyProductBoardFavService {

    @Autowired
    private MyProductBoardFavDAO mpbfDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QProductBoardBookmark qProductBoardBookmark = QProductBoardBookmark.productBoardBookmark;
    private final  QProductBoard qProductBoard = QProductBoard.productBoard;
    private final QAnimalCategory qAnimalCategory = QAnimalCategory.animalCategory;

    // 북마크한 상품 목록 출력
    public Page<ProductBoardBookmark> myFavList(Pageable pageable, BooleanBuilder builder) {


        return mpbfDAO.findAll(builder, pageable);
    }



}
