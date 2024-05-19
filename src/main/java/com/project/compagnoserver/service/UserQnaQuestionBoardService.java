package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.UserQnaBoard.*;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaAnswerChooseDAO;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaQuestionBoardDAO;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaQuestionBoardImageDAO;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaQuestionLikeDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@Service @Slf4j
public class UserQnaQuestionBoardService {

    @Autowired
    private UserQnaQuestionBoardDAO dao;

    @Autowired
    private UserQnaQuestionBoardImageDAO image;

    @Autowired
    private UserQnaAnswerChooseDAO choose;

    @Autowired
    private UserQnaQuestionLikeDAO like;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QUserQnaQuestionBoard qUserQnaQuestionBoard = QUserQnaQuestionBoard.userQnaQuestionBoard;
    private final QUserQnaQuestionLike qUserQnaQuestionLike = QUserQnaQuestionLike.userQnaQuestionLike;
    // 1. 질문 등록
    public UserQnaQuestionBoard create(UserQnaQuestionBoard vo){
        return dao.save(vo);
    }

    // 1-1. 질문 등록 시 이미지 등록
    public UserQnaQuestionBoardImage createImg(UserQnaQuestionBoardImage vo){
        return image.save(vo);
    }

    // 2. 질문 전체 보기
    public Page<UserQnaQuestionBoard> viewAll(BooleanBuilder builder, Pageable pageable){
        return dao.findAll(builder, pageable);
    }

    // 2-1. 좋아요한 질문 전체 보기
    public Page<UserQnaQuestionBoard> viewliked(List<UserQnaQuestionBoard> list, Pageable pageable) {
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<UserQnaQuestionBoard> sublist = list.subList(start, end);

        // 리스트를 역순으로 출력
        Collections.reverse(sublist);

        return new PageImpl<>(sublist, pageable, list.size());
    }

    // 3-0. 질문 상세보기 시 조회수 업데이트
    @Transactional
    public void updateviewcount(int code){
        queryFactory.update(qUserQnaQuestionBoard)
                .set(qUserQnaQuestionBoard.viewcount, qUserQnaQuestionBoard.viewcount.add(1))
                .where(qUserQnaQuestionBoard.userQuestionBoardCode.eq(code))
                .execute();
    }
    // 3. 질문 상세보기
    public UserQnaQuestionBoard view(int code){
        return dao.findById(code).orElse(null);
    }

    // 3-1. 질문 상세보기 시 이미지 가져오기
    public List<UserQnaQuestionBoardImage> viewImg(int code){
        return image.findByQCode(code);
    }

    // 4. 질문 수정하기
    public UserQnaQuestionBoard update(UserQnaQuestionBoard vo){
        if(dao.existsById(vo.getUserQuestionBoardCode())){
            return dao.save(vo);
        }
        return null;
    }

    // 5. 질문 삭제하기
    public UserQnaQuestionBoard delete(int code){
        UserQnaQuestionBoard target = dao.findById(code).orElse(null);
        if(target != null){
            dao.delete(target);
        }
        return target;
    }

    // 5-1. 질문 삭제 시 이미지 삭제하기
    public void deleteImg(int code){
        image.deleteById(code);
    }

    // 6-1. 질문 채택하기
    public void chooseAnswer(UserQnaAnswerChoose vo){
        choose.save(vo);
    }

    // 6-2. 채택 취소하기
    public void deleteChoose(int code){
        choose.deleteById(code);
    }

    // 6-3. 채택 질문 찾기
    public UserQnaAnswerChoose getChoose(int code){
       return choose.findByQCode(code);
    }

    // 7-1. 질문 좋아요
    public UserQnaQuestionLike addLike(UserQnaQuestionLike vo){
        return like.save(vo);
    }

    // 7-2. 좋아요 시 update
    @Transactional
    public void updatelikecount(int code){
        queryFactory.update(qUserQnaQuestionBoard)
                .set(qUserQnaQuestionBoard.likecount, qUserQnaQuestionBoard.likecount.add(1))
                .where(qUserQnaQuestionBoard.userQuestionBoardCode.eq(code))
                .execute();
    }

    // 7-3. 좋아요 확인하기
    public UserQnaQuestionLike selectLike(UserQnaQuestionLike vo){
        return queryFactory.select(qUserQnaQuestionLike)
                .from(qUserQnaQuestionLike)
                .where(qUserQnaQuestionLike.userQuestionBoardCode.eq(vo.getUserQuestionBoardCode())
                        .and(qUserQnaQuestionLike.userId.eq(vo.getUserId()))).fetchOne();

    }

    // 7-4. 좋아요 취소하기
    public void deleteLike(UserQnaQuestionLike vo){
        UserQnaQuestionLike result = selectLike(vo);
        like.deleteById(result.getUserQuestionLikeCode());
    }

    // 7-5. 좋아요 취소 시 update
    @Transactional
    public void discountlikecount(int code){
        queryFactory.update(qUserQnaQuestionBoard)
                .set(qUserQnaQuestionBoard.likecount, qUserQnaQuestionBoard.likecount.add(-1))
                .where(qUserQnaQuestionBoard.userQuestionBoardCode.eq(code))
                .execute();
    }
}
