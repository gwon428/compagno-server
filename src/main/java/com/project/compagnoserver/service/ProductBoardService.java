package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Product.*;
import com.project.compagnoserver.repo.ProductBoardBookmarkDAO;
import com.project.compagnoserver.repo.ProductBoardDAO;
import com.project.compagnoserver.repo.ProductBoardImageDAO;
import com.project.compagnoserver.repo.ProductBoardRecommendDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 이미지 삭제
    public void deleteImage(int code) {
        queryFactory.delete(qProductBoardImage)
                .where(qProductBoardImage.productBoard.productBoardCode.eq(code))
                .execute();
    }

    // 게시판 수정
    public void updateBoard(ProductBoard vo) {
        if(board.existsById(vo.getProductBoardCode())) {
            board.save(vo);
        }
    }

    // 이미지 수정
    public void updateImage(ProductBoardImage vo) {
        deleteImage(vo.getProductBoard().getProductBoardCode());
        createImage(vo);
    }

    // 게시판 북마크
    public void boardBookmark(ProductBoardBookmark vo) {
        Integer checkBookmark = queryFactory.selectOne()
                .from(qProductBoardBookmark)
                .where(qProductBoardBookmark.productBoardCode.eq(vo.getProductBoardCode()))
                //.where(qProductBoardBookmark.user.userId.eq(vo.getUser().getUserId()))
                .fetchFirst();
        if(checkBookmark==null) {
            bookmark.save(vo);
        } else {
            bookmark.deleteById(checkBookmark);
            bookmark.save(vo);
        }
    }

    // 게시판 추천
    public void boardRecommend(ProductBoardRecommend vo) {
        Integer checkRecommend = queryFactory.selectOne()
                .from(qProductBoardRecommend)
                .where(qProductBoardRecommend.productBoardCode.eq(vo.getProductBoardCode()))
                //.where(qProductBoardRecommend.user.userId.eq(vo.getUser().getUserId()))
                .fetchFirst();
        if(checkRecommend==null) {
            recommend.save(vo);
        } else {
            recommend.deleteById(checkRecommend);
            recommend.save(vo);
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


}
