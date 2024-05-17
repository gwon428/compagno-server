package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalCategory;
import com.project.compagnoserver.domain.Parsing.LocationParsing;
import com.project.compagnoserver.domain.Parsing.QLocationParsing;
import com.project.compagnoserver.domain.SitterBoard.*;
import com.project.compagnoserver.repo.Animal.AnimalCategoryDAO;
import com.project.compagnoserver.repo.Parsing.LocationParsingDAO;
import com.project.compagnoserver.repo.SitterBoard.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Slf4j
public class SitterBoardService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private SitterBoardDAO sitterBoardDAO;
    private final QSitterBoard qSitterBoard = QSitterBoard.sitterBoard;

    @Autowired
    private SitterCategoryDAO sitterCategoryDAO;

    @Autowired
    private AnimalCategoryDAO animalCategoryDAO;

    @Autowired
    private SitterBoardImageDAO sitterBoardImageDAO;
    private final QSitterBoardImage qSitterBoardImage = QSitterBoardImage.sitterBoardImage;

    @Autowired
    private SitterCommentDAO sitterCommentDAO;
    private final QSitterBoardComment qSitterBoardComment = QSitterBoardComment.sitterBoardComment;

    @Autowired
    private SitterBoardBookmarkDAO sitterBoardBookmarkDAO;
    private final QSitterBoardBookmark qSitterBoardBookmark = QSitterBoardBookmark.sitterBoardBookmark;

    @Autowired
    private LocationParsingDAO locationParsingDAO;
    private final QLocationParsing qLocationParsing = QLocationParsing.locationParsing;


    // 카테고리 전체보기
    public List<SitterCategory> sitterCategoryView() {

        return sitterCategoryDAO.findAll();
    }

    // 동물 카테고리 전체보기
    public List<AnimalCategory> animalCategoryView() {
        return animalCategoryDAO.findAll();
    }


    // 전체 보기
    public Page<SitterBoard> sitterViewAll(Pageable pageable, BooleanBuilder builder) {
        return sitterBoardDAO.findAll(builder, pageable);
    }
    public List<SitterBoardImage> sitterViewAllImg(int code) {return sitterBoardImageDAO.findByBoardCode(code);}


    // 상세 보기
    public SitterBoard sitterView(int code) {
        return sitterBoardDAO.findById(code).orElse(null);
    }

    public List<SitterBoardImage> sitterViewImg(int code) {
        return queryFactory.selectFrom(qSitterBoardImage)
                .where(qSitterBoardImage.sitterBoard.sitterBoardCode.eq(code))
                .fetch();
    }


    // 조회수
    @Transactional
    public void sitterViewCount(int code) {
        queryFactory.update(qSitterBoard)
                .set(qSitterBoard.sitterViewCount, qSitterBoard.sitterViewCount.add(1))
                .where(qSitterBoard.sitterBoardCode.eq(code))
                .execute();
    }


    // 글 등록
    public SitterBoard sitterCreate(SitterBoard sitter) {
        return sitterBoardDAO.save(sitter);
    }
    public void sitterCreateImg(SitterBoardImage sitterImg) {
        sitterBoardImageDAO.save(sitterImg);
    }


    // 글 수정
    public void sitterUpdate(SitterBoard sitter) {
        if(sitterBoardDAO.existsById(sitter.getSitterBoardCode())) {
            sitterBoardDAO.save(sitter);
        }
    }
    public void sitterUpdateImg(SitterBoardImage sitterImg) {
        sitterDeleteImg(sitterImg.getSitterBoard().getSitterBoardCode());
        sitterCreateImg(sitterImg);
    }


    // 글 삭제
    public void sitterDelete(int code) {
        SitterBoard target = sitterBoardDAO.findById(code).orElse(null);
        if(target != null) {
            sitterBoardDAO.delete(target);
        }
    }

    @Transactional
    public void sitterDeleteImg(int code) {
        queryFactory.delete(qSitterBoardImage)
                .where(qSitterBoardImage.sitterBoard.sitterBoardCode.eq(code))
                .execute();
    }


// ====================================== 북마크 ======================================
    public Integer checkBookmark (SitterBoardBookmark bookmarkVo) {
        return queryFactory.select(qSitterBoardBookmark.sitterBookmarkCode)
                .from(qSitterBoardBookmark)
                .where(qSitterBoardBookmark.sitterBoard.sitterBoardCode.eq(bookmarkVo.getSitterBoard().getSitterBoardCode()))
                .where(qSitterBoardBookmark.user.userId.eq(bookmarkVo.getUser().getUserId()))
                .fetchOne();
    }

    public void sitterBoardBookmark(SitterBoardBookmark bookmarkVo) {
        if(checkBookmark(bookmarkVo) == null) {
            sitterBoardBookmarkDAO.save(bookmarkVo);
        } else {
            sitterBoardBookmarkDAO.deleteById(checkBookmark(bookmarkVo));
        }
    }



// ====================================== 댓글 ======================================

    // 댓글 하나 보기
    public SitterBoardComment sitterCommentview(int code){
        return sitterCommentDAO.findById(code).orElse(null);
    }

    // 댓글 추가
    public SitterBoardComment sitterCommentCreate(SitterBoardComment sitterBoardComment) {
        return sitterCommentDAO.save(sitterBoardComment);
    }

    // 댓글 수정
    public void sitterCommentUpdate(SitterBoardComment sitterBoardComment) {
//        if(sitterCommentDAO.existsById(sitterBoardComment.getSitterCommentCode())) {
//            sitterCommentDAO.save(sitterBoardComment);
//        } else {
//        }
        if(sitterCommentDAO.existsById(sitterBoardComment.getSitterCommentCode())) {
            queryFactory.update(qSitterBoardComment)
                    .set(qSitterBoardComment.sitterCommentContent, sitterBoardComment.getSitterCommentContent())
                    .where(qSitterBoardComment.sitterCommentCode.eq(sitterBoardComment.getSitterCommentCode()))
                    .execute();
        }
    }

    // 댓글 삭제
    @Transactional
    public void sitterCommentDelete(int commentCode) {
        SitterBoardComment target = sitterCommentDAO.findById(commentCode).orElse(null);
        if(target != null) {
            sitterCommentDAO.delete(target);
        }
//        if(sitterCommentDAO.existsById(commentCode)) {
//            queryFactory.update(qSitterBoardComment)
//                    .set(qSitterBoardComment.sitterCommentStatus, "N")
//                    .where(qSitterBoardComment.sitterCommentCode.eq(commentCode))
//                    .execute();
//        }
    }

    // 원 댓글만 조회
    public List<SitterBoardComment> getTopComments(int code) {
        return queryFactory.selectFrom(qSitterBoardComment)
                .where(qSitterBoardComment.sitterCommentParentCode.eq(0))
                .where(qSitterBoardComment.sitterBoardCode.eq(code))
                .orderBy(qSitterBoardComment.sitterCommentCode.desc())
                .fetch();
    }

    // 대댓글만 조회
    public List<SitterBoardComment> getReplyComments(int parent, int code) {
        return queryFactory.selectFrom(qSitterBoardComment)
                .where(qSitterBoardComment.sitterCommentParentCode.eq(parent))
                .where(qSitterBoardComment.sitterBoardCode.eq(code))
                .orderBy(qSitterBoardComment.sitterCommentRegiDate.desc())
                .fetch();
    }


// ====================================== Location ======================================

    // 시도 조회
    public List<LocationParsing> sitterGetProvinces() {
        return queryFactory.selectFrom(qLocationParsing)
                .where(qLocationParsing.locationParentCode.eq(0))
                .fetch();
    }

    // 시도별 시군구 조회
    public List<LocationParsing> sitterGetDistricts(int code) {
        return queryFactory.selectFrom(qLocationParsing)
                .where(qLocationParsing.locationParentCode.eq(code))
                .fetch();
    }
}
