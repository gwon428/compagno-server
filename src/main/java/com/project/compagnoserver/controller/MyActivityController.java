package com.project.compagnoserver.controller;

import com.project.compagnoserver.config.TokenProvider;
import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import com.project.compagnoserver.domain.Animal.QAnimalBoardFavorite;
import com.project.compagnoserver.service.MyActivityService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class MyActivityController {

    @Autowired
    private MyActivityService maService;

    @Autowired
    private TokenProvider tokenProvider;



    // 내 좋아요 목록 출력
    @GetMapping("/api/mypage/myactivity/{id}")
    public ResponseEntity<List<AnimalBoardFavorite>> myFavList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1") int page) {
        Sort sort = Sort.by("animalFavoriteDate").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QAnimalBoardFavorite qAnimalBoardFavorite = QAnimalBoardFavorite.animalBoardFavorite;

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qAnimalBoardFavorite.user.userId.eq(id);

        builder.and(expression);

        Page<AnimalBoardFavorite> list = maService.myFavList(pageable, builder);

        return ResponseEntity.ok(list.getContent());


    }


}
