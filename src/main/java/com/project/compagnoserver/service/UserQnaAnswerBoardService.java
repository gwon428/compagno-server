package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.UserQnaBoard.QUserQnaAnswerBoard;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaAnswerBoard;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaAnswerBoardDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j
public class UserQnaAnswerBoardService {
    @Autowired
    private UserQnaAnswerBoardDAO dao;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QUserQnaAnswerBoard qUserQnaAnswerBoard = QUserQnaAnswerBoard.userQnaAnswerBoard;

    // 1. Answer 추가
    public UserQnaAnswerBoard create (UserQnaAnswerBoard vo){
        return dao.save(vo);
    }

    // 2. 상위 Answer만 조회
    public List<UserQnaAnswerBoard> getTopLevelAnswers (int code){
        return queryFactory.selectFrom(qUserQnaAnswerBoard)
                .where(qUserQnaAnswerBoard.answerParentCode.eq(0))
                .where(qUserQnaAnswerBoard.userQuestionBoardCode.eq(code))
                .orderBy(qUserQnaAnswerBoard.userAnswerDate.asc())
                .fetch();
    }

    // 3. 하위 Answer 조회
    public List<UserQnaAnswerBoard> getBottomLevelAnswers(int parent, int code){
        return queryFactory.selectFrom(qUserQnaAnswerBoard)
                .where(qUserQnaAnswerBoard.answerParentCode.eq(parent))
                .where(qUserQnaAnswerBoard.userQuestionBoardCode.eq(code))
                .orderBy(qUserQnaAnswerBoard.userAnswerDate.asc())
                .fetch();
    }

    public List<UserQnaAnswerBoard> getBottomLevelAnswers(int parent){
        return queryFactory.selectFrom(qUserQnaAnswerBoard)
                .where(qUserQnaAnswerBoard.answerParentCode.eq(parent))
                .fetch();
    }

    // 4-1. 수정할 Answer 찾기
    public UserQnaAnswerBoard viewAnswer(int code){
        log.info("code : " + code);
        return dao.findById(code).orElse(null);
    }

    // 4-2. Answer 수정
    public UserQnaAnswerBoard update(UserQnaAnswerBoard vo){
        UserQnaAnswerBoard answer = dao.findById(vo.getUserAnswerBoardCode()).orElse(null);
        if(answer != null){
            return dao.save(vo);
        }
        return null;
    }

    // 5-1. Answer 삭제
    public void deleteAnswer (int code){
        UserQnaAnswerBoard vo = dao.findById(code).orElse(null);
        if(vo != null){
            dao.delete(vo);
        }
    }



}