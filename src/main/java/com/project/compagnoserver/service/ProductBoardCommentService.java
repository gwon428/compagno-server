package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.ProductBoard.ProductBoardComment;
import com.project.compagnoserver.domain.ProductBoard.ProductBoardRecommend;
import com.project.compagnoserver.domain.ProductBoard.QProductBoardComment;
import com.project.compagnoserver.repo.ProductBoard.ProductBoardCommentDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductBoardCommentService {

    @Autowired
    private ProductBoardCommentDAO dao;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QProductBoardComment qProductBoardComment = QProductBoardComment.productBoardComment;

    // 댓글 작성
    public ProductBoardComment create(ProductBoardComment vo) {
        return dao.save(vo);}

    // 댓글 수정
    @Transactional
    public void update(ProductBoardComment vo) {
        if(dao.existsById(vo.getProductCommentCode())) {
              queryFactory.update(qProductBoardComment)
                    .set(qProductBoardComment.productCommentContent, vo.getProductCommentContent())
                    .where(qProductBoardComment.productCommentCode.eq(vo.getProductCommentCode()))
                    .execute();
        }
    }

    // 댓글 삭제
    @Transactional
    public void delete(int code) {
        if(dao.existsById(code)) {
            queryFactory.update(qProductBoardComment)
                    .set(qProductBoardComment.productCommentDelete, 'y')
                    .where(qProductBoardComment.productCommentCode.eq(code))
                    .execute();
        }
    }

    // 댓글 조회
    public List<ProductBoardComment> getTopLevelComments(int code) {
        return queryFactory.selectFrom(qProductBoardComment)
                .where(qProductBoardComment.productParentCode.eq(0))
                .where(qProductBoardComment.productBoardCode.eq(code))
                .orderBy(qProductBoardComment.productCommentRegiDate.asc())
                .fetch();
    }

    // 대댓글 조회
    public List<ProductBoardComment> getBottomComments(int parent, int code) {

        return queryFactory.selectFrom(qProductBoardComment)
                .where(qProductBoardComment.productParentCode.eq(parent))
                .where(qProductBoardComment.productBoardCode.eq(code))
                .orderBy(qProductBoardComment.productCommentRegiDate.asc())
                .fetch();

    }
}
