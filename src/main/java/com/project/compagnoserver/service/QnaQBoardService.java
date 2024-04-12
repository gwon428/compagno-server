package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.QnaQBoard;
import com.project.compagnoserver.domain.QnaQBoardImage;
import com.project.compagnoserver.repo.QnaQBoardDAO;
import com.project.compagnoserver.repo.QnaQBoardImageDAO;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j
public class QnaQBoardService {

    @Autowired
    private QnaQBoardDAO dao;

    @Autowired
    private QnaQBoardImageDAO image;

    public QnaQBoard create(QnaQBoard vo){
        return dao.save(vo);
    }

    public QnaQBoardImage createImg(QnaQBoardImage vo){
        return image.save(vo);
    }

    public Page<QnaQBoard> viewAll(BooleanBuilder builder, Pageable pageable){
        return dao.findAll(builder, pageable);
    }

    public QnaQBoard view(int code){
        return dao.findById(code).orElse(null);
    }

    public QnaQBoard update(QnaQBoard vo){
        // 수정할 해당 객체가 있는 경우
        if(dao.existsById(vo.getQnaQBoardCode())){
            return dao.save(vo);
        }
        return null;
    }

    public QnaQBoard delete(int code){
        QnaQBoard target = dao.findById(code).orElse(null);
        if(target != null){
            dao.delete(target);
        }
        return target;
    }
}

