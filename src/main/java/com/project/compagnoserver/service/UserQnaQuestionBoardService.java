package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.UserQnaBoard.UserQnaAnswerChoose;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoard;
import com.project.compagnoserver.domain.UserQnaBoard.UserQnaQuestionBoardImage;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaAnswerChooseDAO;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaQuestionBoardDAO;
import com.project.compagnoserver.repo.UserQnaBoard.UserQnaQuestionBoardImageDAO;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service @Slf4j
public class UserQnaQuestionBoardService {

    @Autowired
    private UserQnaQuestionBoardDAO dao;

    @Autowired
    private UserQnaQuestionBoardImageDAO image;

    @Autowired
    private UserQnaAnswerChooseDAO choose;


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
}
