package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import com.project.compagnoserver.domain.Animal.QAnimalBoardFavorite;
import com.project.compagnoserver.domain.ProductBoard.ProductBoardBookmark;
import com.project.compagnoserver.domain.ProductBoard.QProductBoardBookmark;
import com.project.compagnoserver.domain.QnaQ.QQnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.service.MyAnimalBoardFavService;
import com.project.compagnoserver.service.MyPageQnaService;
import com.project.compagnoserver.service.MyProductBoardFavService;
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

    // 동물게시판 서비스
    @Autowired
    private MyAnimalBoardFavService mabfService;

    // 상품목록 게시판 서비스
    @Autowired
    private MyProductBoardFavService mpbfService;

    // QnA 서비스
    @Autowired
    private MyPageQnaService myQnaService;


    // 최애 동물 좋아요 목록 출력
    @GetMapping("/api/mypage/myactivity/animalfav/{id}")
    public ResponseEntity<List<AnimalBoardFavorite>> myFavList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1") int page) {
        Sort sort = Sort.by("animalFavoriteDate").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QAnimalBoardFavorite qAnimalBoardFavorite = QAnimalBoardFavorite.animalBoardFavorite;

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qAnimalBoardFavorite.user.userId.eq(id);

        builder.and(expression);

        Page<AnimalBoardFavorite> list = mabfService.myFavList(pageable, builder);

        return ResponseEntity.ok(list.getContent());
    }

    // 최애 동물 좋아요 갯수 출력
    @GetMapping("/api/mypage/myactivity/countanimalfav/{id}")
    public ResponseEntity countFav(@PathVariable("id") String id) {
        return ResponseEntity.ok(mabfService.countFav(id));
    }


    // 북마크한 상품 목록 출력
        @GetMapping("/api/mypage/myactivity/productfav/{id}")
    public ResponseEntity<List<ProductBoardBookmark>> myProFavList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1") int page) {
        Sort sort = Sort.by("ProductBookmarkCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

            QProductBoardBookmark qProductBoardBookmark = QProductBoardBookmark.productBoardBookmark;

            BooleanBuilder builder = new BooleanBuilder();

            BooleanExpression expression = qProductBoardBookmark.user.userId.eq(id);

            builder.and(expression);

            Page<ProductBoardBookmark> list = mpbfService.myFavList(pageable, builder);

            return ResponseEntity.ok(list.getContent());
        }

    // 일반유저 - 내가 작성한 질문 리스트
    @GetMapping("/api/mypage/myactivity/myqna/{id}")
    public ResponseEntity<List<QnaQBoard>> userQnaList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1") int page) {
        Sort sort = Sort.by("qnaQCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QQnaQBoard qQnaQBoard = QQnaQBoard.qnaQBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qQnaQBoard.userId.eq(id);
       builder.and(expression);

       Page<QnaQBoard> list = myQnaService.userQnaList(pageable, builder);


        return ResponseEntity.ok(list.getContent());

    }
    // 일반유저 - 페이징 위해 내가 작성한 질문 갯수
    @GetMapping("/api/mypage/myactivity/myqna/count/{id}")
    public ResponseEntity countQna(@PathVariable("id") String id) {
        return ResponseEntity.ok(myQnaService.countQna(id));
    }

    // 관리자 - 미답변 질문 리스트
    @GetMapping("/api/mypage/myactivity/manageqna")
    public ResponseEntity managerQnaList(@RequestParam(name = "page",defaultValue = "1") int page) {
        Sort sort = Sort.by("qnaQCode");
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QQnaQBoard qQnaQBoard = QQnaQBoard.qnaQBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qQnaQBoard.qnaQStatus.eq("N");
        builder.and(expression);

        Page<QnaQBoard> list = myQnaService.managerQnaList(pageable, builder);

        return ResponseEntity.ok(list.getContent());
    }

    // 관리자 - 미답변 질문 갯수
    @GetMapping("/api/mypage/myactivity/manageqna/count")
    public ResponseEntity countmanagerQna() {
        return ResponseEntity.ok(myQnaService.countmanagerQna());

    }


}
