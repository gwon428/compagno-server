package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.ProductBoard.*;
import com.project.compagnoserver.repo.ProductBoard.ProductBoardBookmarkDAO;
import com.project.compagnoserver.repo.ProductBoard.ProductBoardDAO;
import com.project.compagnoserver.repo.ProductBoard.ProductBoardImageDAO;
import com.project.compagnoserver.repo.ProductBoard.ProductBoardRecommendDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Objects;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductBoardService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private ProductBoardDAO board;
    private final QProductBoard qProductBoard = QProductBoard.productBoard;

    @Autowired
    private ProductBoardImageDAO image;

    private final QProductBoardImage qProductBoardImage = QProductBoardImage.productBoardImage;

    @Autowired
    private ProductBoardBookmarkDAO bookmark;

    private final QProductBoardBookmark qProductBoardBookmark = QProductBoardBookmark.productBoardBookmark;

    @Autowired
    private ProductBoardRecommendDAO recommend;

    private final QProductBoardRecommend qProductBoardRecommend = QProductBoardRecommend.productBoardRecommend;

    private final QProductBoardComment qProductBoardComment = QProductBoardComment.productBoardComment;

    public ProductBoard viewBoard(int code){
        return board.findById(code).orElse(null);
    }

    public List<ProductBoardImage> viewImage(int code) {
        return queryFactory.selectFrom(qProductBoardImage)
                .where(qProductBoardImage.productBoard.productBoardCode.eq(code))
                .fetch();
    }

    // 게시판 작성
    public ProductBoard createBoard(ProductBoard vo) {
        return board.save(vo);
    }

    // 이미지 저장
    public void createImage(ProductBoardImage vo) {
        image.save(vo);
    }

    // 게시판 삭제
    public void deleteBoard(int code) {
        if(board.existsById(code)) {
            board.deleteById(code);
        }
    }

    // 게시판 한개 이미지 삭제
    @Transactional
    public void deleteImage(int code) {
        queryFactory.delete(qProductBoardImage)
                .where(qProductBoardImage.productBoard.productBoardCode.eq(code))
                .execute();
    }

    // 댓글 한개 삭제
    public void deleteOneImage(int code) {
        image.deleteById(code);
    }

    // 게시판 수정
    public ProductBoard updateBoard(ProductBoard vo) {
        if(board.existsById(vo.getProductBoardCode())) {
            return board.save(vo);
        }
        return null;
    }
    
    // 게시판 북마크 확인
    public Integer checkBookmark (ProductBoardBookmark vo) {
        return queryFactory.select(qProductBoardBookmark.productBookmarkCode)
                .from(qProductBoardBookmark)
                .where(qProductBoardBookmark.productBoard.productBoardCode.eq(vo.getProductBoard().getProductBoardCode()))
                .where(qProductBoardBookmark.user.userId.eq(vo.getUser().getUserId()))
                .fetchOne();

    }
    // 게시판 북마크
    public void boardBookmark(ProductBoardBookmark vo) {
        if(checkBookmark(vo)==null) {
            bookmark.save(vo);
        } else {
            bookmark.deleteById(checkBookmark(vo));
        }
    }

    // 게시판 추천 확인
    public Integer checkRecommend (ProductBoardRecommend vo) {

        return queryFactory.select(qProductBoardRecommend.productRecommendCode)
                .from(qProductBoardRecommend)
                .where(qProductBoardRecommend.productBoard.productBoardCode.eq(vo.getProductBoard().getProductBoardCode()))
                .where(qProductBoardRecommend.user.userId.eq(vo.getUser().getUserId()))
                .fetchOne();
    }

    // 게시판 추천
    public void boardRecommend(ProductBoardRecommend vo) {
        log.info("check : " + checkRecommend(vo));
        if(checkRecommend(vo)==null) {
            recommend.save(vo);
        } else {
            recommend.deleteById(checkRecommend(vo));
        }
    }

    // 조회수
    @Transactional
    public void viewCountUp(int code) {
        queryFactory.update(qProductBoard)
                .set(qProductBoard.productBoardViewCount, qProductBoard.productBoardViewCount.add(1))
                .where(qProductBoard.productBoardCode.eq(code))
                .execute();
    }

    // 검색, 전체보기
    public Page<ProductBoard> searchProductBoard(ProductBoardSearchDTO dto, Pageable pageable) {

        String sort = dto.getSort();

        // 검색 필터
        BooleanBuilder builder = new BooleanBuilder();
        // 동물 카테고리
        if (dto.getAnimal() != null) {
            builder.and(qProductBoard.animalCategory.animalCategoryCode.eq(dto.getAnimal()));
        }
        // 평점
        if (dto.getGrade() != null) {
            builder.and(qProductBoard.productBoardGrade.goe(dto.getGrade()));
        }
        // 상품 카테고리
        if (dto.getProductCate() != null && !dto.getProductCate().isEmpty()) {
            builder.and(qProductBoard.productCategory.eq(dto.getProductCate()));
        }
        // 최소 가격
        if (dto.getMinPrice() != null) {
            builder.and(qProductBoard.productPrice.goe(dto.getMinPrice()));
        }
        // 최대 가격
        if (dto.getMaxPrice() != null) {
            builder.and(qProductBoard.productPrice.loe(dto.getMaxPrice()));
        }

        // 키워드 검색
        if (dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            switch (dto.getSelect()) {
                case "title": // 제목 검색
                    builder.and(qProductBoard.productBoardTitle.contains(dto.getKeyword()));
                    break;
                case "nickname": // 닉네임 검색
                    builder.and(qProductBoard.user.userNickname.contains(dto.getKeyword()));
                    break;
                case "content": // 내용 검색
                    builder.and(qProductBoard.productBoardContent.contains(dto.getKeyword()));
                    break;
                case "all": // 제목 + 내용 검색
                    builder.and(qProductBoard.productBoardTitle.contains(dto.getKeyword()));
                    builder.or(qProductBoard.productBoardContent.contains(dto.getKeyword()));
                    break;
            }
        }

        // 게시판 리스트
        List<ProductBoard> list = queryFactory.select(qProductBoard)
                .from(qProductBoard)
                .where(builder) // 필터 적용
                .orderBy(productBoardSort(sort)) // 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 게시판 수 카운트
        int count =queryFactory.selectFrom(qProductBoard)
                .where(builder)
                .fetch().size();

        // 페이징 처리
        return new PageImpl<>(list, pageable, count);
    }

    // 동적 정렬(조건 추가 필요)
    private OrderSpecifier<?>[] productBoardSort (String sort) {

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        if(sort!= null && sort.equals("view")) { // 조회수 순 DESC
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, qProductBoard.productBoardViewCount));
        }

        orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, qProductBoard.productBoardRegiDate)); // 날짜 순 DESC

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}
