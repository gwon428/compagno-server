package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoard;
import com.project.compagnoserver.domain.AdoptionBoard.QAdoptionBoard;
import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import com.project.compagnoserver.domain.Animal.QAnimalBoardFavorite;
import com.project.compagnoserver.domain.Animal.QAnimalCategory;
import com.project.compagnoserver.domain.LostBoard.LostBoard;
import com.project.compagnoserver.domain.LostBoard.QLostBoard;
import com.project.compagnoserver.domain.NeighborBoard.NeighborBoard;
import com.project.compagnoserver.domain.NeighborBoard.NeighborBoardComment;
import com.project.compagnoserver.domain.NeighborBoard.QNeighborBoard;
import com.project.compagnoserver.domain.NeighborBoard.QNeighborBoardComment;
import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.domain.OneDayClass.QClassBoard;
import com.project.compagnoserver.domain.ProductBoard.ProductBoardBookmark;
import com.project.compagnoserver.domain.ProductBoard.QProductBoard;
import com.project.compagnoserver.domain.ProductBoard.QProductBoardBookmark;
import com.project.compagnoserver.domain.QnaQ.QQnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.domain.SitterBoard.QSitterBoard;
import com.project.compagnoserver.domain.SitterBoard.QSitterBoardComment;
import com.project.compagnoserver.domain.SitterBoard.SitterBoard;
import com.project.compagnoserver.domain.SitterBoard.SitterBoardComment;
import com.project.compagnoserver.domain.UserQnaBoard.QUserQnaQuestionBoard;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoard;
import com.project.compagnoserver.service.*;
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

    // 입양공고 서비스
    @Autowired
    private MyAdoptionService adopService;

    // 실종공고 서비스
    @Autowired
    private MyLostService lostService;

    // 유저-유저 질문 서비스
    @Autowired
    private MyUserQnaService userQnaService;

    // 내 원데이클래스 서비스
    @Autowired
    private MyOdcService odcService;

    // 내 시터 서비스
    @Autowired
    private MySitterService sitterService;

    // 내 시터게시판 댓글 서비스
    @Autowired
    private MySitterComService sitterComService;

    // 우리동네 게시판 게시글 서비스
    @Autowired
    private MyNeighborPostService neighborPostService;

    // 우리동네 게시판 댓글 서비스
    @Autowired
    private MyNeighborComService neighborComService;


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
            QProductBoard qProductBoard = QProductBoard.productBoard;
            QAnimalCategory qAnimalCategory = QAnimalCategory.animalCategory;

            BooleanBuilder builder = new BooleanBuilder();
            BooleanExpression expression = qProductBoardBookmark.user.userId.eq(id);
            builder.and(expression);
            Page<ProductBoardBookmark> list = mpbfService.myFavList(pageable, builder);

            return ResponseEntity.ok(list.getContent());
        }

        @GetMapping("/api/mypage/myactivity/productfav/count/{id}")
        public ResponseEntity countBookmark(@PathVariable("id") String id) {
            return ResponseEntity.ok(mpbfService.countBookmark(id));
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

    // 내 입양공고 리스트 출력
    @GetMapping("/api/mypage/myactivity/myadoption/{id}")
    public ResponseEntity myAdopList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1") int page) {
        Sort sort = Sort.by("adopBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 6, sort);

        QAdoptionBoard qAdoptionBoard = QAdoptionBoard.adoptionBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qAdoptionBoard.userId.eq(id);
        builder.and(expression);

        Page<AdoptionBoard> list = adopService.myAdopList(pageable, builder);

        return ResponseEntity.ok(list.getContent());
    }

    // 내 입양공고 갯수 불러오기
    @GetMapping("/api/mypage/myactivity/myadoption/count/{id}")
    public  ResponseEntity countMyAdoption(@PathVariable("id") String id) {
        return ResponseEntity.ok(adopService.countMyAdoption(id));
    }

    // 내 실종동물 리스트 출력
    @GetMapping("/api/mypage/myactivity/mylost/{id}")
    public ResponseEntity myLostList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1") int page) {
        Sort sort = Sort.by("lostBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 6, sort);

        QLostBoard qLostBoard = QLostBoard.lostBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qLostBoard.userId.eq(id);
        builder.and(expression);

        Page<LostBoard> list = lostService.myLostList(pageable, builder);

        return ResponseEntity.ok(list.getContent());
    }

    // 내 실종동물 갯수 불러오기
    @GetMapping("/api/mypage/myactivity/mylost/count/{id}")
    public ResponseEntity countMyLost(@PathVariable("id") String id) {
        return ResponseEntity.ok(lostService.countMyLost(id));
    }

    // 내 유저-유저 질문 리스트 출력
    @GetMapping("/api/mypage/myactivity/myuserqna/{id}")
    public ResponseEntity myUserQnaList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1")int page) {
        Sort sort = Sort.by("userQuestionBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QUserQnaQuestionBoard qUserQnaQuestionBoard = QUserQnaQuestionBoard.userQnaQuestionBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qUserQnaQuestionBoard.userId.eq(id);
        builder.and(expression);

        Page<UserQnaQuestionBoard> list = userQnaService.myUserQnaList(pageable, builder);
        return ResponseEntity.ok(list.getContent());
    }

    // 내 유저-유저 질문 갯수 출력
    @GetMapping("/api/mypage/myactivity/myuserqna/count/{id}")
    public ResponseEntity countMyUserQna(@PathVariable("id") String id) {
        return ResponseEntity.ok(userQnaService.countMyUserQna(id));
    }

    // 내 원데이클래스 리스트 출력
    @GetMapping("/api/mypage/myactivity/myodc/{id}")
    public ResponseEntity myOdcList(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1")int page) {
        Sort sort = Sort.by("odcCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QClassBoard qClassBoard = QClassBoard.classBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qClassBoard.user.userId.eq(id);
        builder.and(expression);

        Page<ClassBoard> list = odcService.myOdcList(pageable, builder);
        return ResponseEntity.ok(list.getContent());
    }

    // 내 원데이클래스 갯수 출력
    @GetMapping("/api/mypage/myactivity/myodc/count/{id}")
    public ResponseEntity countMyOdc(@PathVariable("id") String id) {
        return ResponseEntity.ok(odcService.countMyOdc(id));
    }

    // 내 시터 공고 리스트 출력
    @GetMapping("/api/mypage/myactivity/mysitterpost/{id}")
    public  ResponseEntity mySitterPostList(@PathVariable("id") String id, @RequestParam(name = "postPage",defaultValue = "1")int page) {
        Sort sort = Sort.by("sitterBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QSitterBoard qSitterBoard = QSitterBoard.sitterBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qSitterBoard.user.userId.eq(id);
        builder.and(expression);

        Page<SitterBoard> list = sitterService.mySitterList(pageable, builder);
        return ResponseEntity.ok(list.getContent());
    }

    // 내 시터 공고 갯수 출력
    @GetMapping("/api/mypage/myactivity/mysitterpost/count/{id}")
    public ResponseEntity countMySitterPost(@PathVariable("id") String id) {
        return ResponseEntity.ok(sitterService.countMySitter(id));
    }

    // 내 시터게시판 댓글 리스트
    @GetMapping("/api/mypage/myactivity/mysittercom/{id}")
    public ResponseEntity mySitterComList(@PathVariable("id") String id, @RequestParam(name = "comPage",defaultValue = "1")int page) {
        Sort sort = Sort.by("sitterCommentCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QSitterBoardComment qSitterBoardComment = QSitterBoardComment.sitterBoardComment;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qSitterBoardComment.user.userId.eq(id);
        builder.and(expression);

        Page<SitterBoardComment> list = sitterComService.mySitterComList(pageable, builder);
        return  ResponseEntity.ok(list.getContent());
    }

    // 내 시터게시판 댓글 갯수
    @GetMapping("/api/mypage/myactivity/mysittercom/count/{id}")
    public ResponseEntity countMySitterCom(@PathVariable("id") String id) {
        return ResponseEntity.ok(sitterComService.countMySitterCom(id));
    }

    // 우리동네 게시판 게시글 리스트
    @GetMapping("/api/mypage/myactivity/myneighborpost/{id}")
    public ResponseEntity myNeighborPost(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1")int page) {
        Sort sort = Sort.by("neighborBoardCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QNeighborBoard qNeighborBoard = QNeighborBoard.neighborBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qNeighborBoard.user.userId.eq(id);
        builder.and(expression);

        Page<NeighborBoard> list = neighborPostService.myNeighborPost(pageable, builder);
        return ResponseEntity.ok(list.getContent());
    }

    // 우리동네 게시판 게시글 갯수
    @GetMapping("/api/mypage/myactivity/myneighborpost/count/{id}")
    public ResponseEntity CountMyNeighborPost(@PathVariable("id") String id) {
        return ResponseEntity.ok(neighborPostService.CountMyNeighborPost(id));
    }

    // 우리동네 게시판 댓글 리스트
    @GetMapping("/api/mypage/myactivity/myneighborcom/{id}")
    public ResponseEntity myNeighborCom(@PathVariable("id") String id, @RequestParam(name = "page",defaultValue = "1")int page) {
        Sort sort = Sort.by("neighborCommentCode").descending();
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        QNeighborBoardComment qNeighborBoardComment = QNeighborBoardComment.neighborBoardComment;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qNeighborBoardComment.user.userId.eq(id);
        builder.and(expression);

        Page<NeighborBoardComment> list = neighborComService.myNeighborCom(pageable, builder);
        return ResponseEntity.ok(list.getContent());
    }

    // 우리동네 게시판 댓글 갯수
    @GetMapping("/api/mypage/myactivity/myneighborcom/count/{id}")
    public ResponseEntity countmyNeighborCom(@PathVariable("id") String id) {
        return ResponseEntity.ok(neighborComService.countmyNeighborCom(id));
    }
}
