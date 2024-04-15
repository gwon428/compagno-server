package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.QnaA.QnaABoard;
import com.project.compagnoserver.domain.QnaA.QnaABoardImage;
import com.project.compagnoserver.domain.QnaQ.QnaQBoard;
import com.project.compagnoserver.domain.QnaQ.QnaQBoardImage;
import com.project.compagnoserver.repo.Qna.QnaABoardDAO;
import com.project.compagnoserver.repo.Qna.QnaABoardImageDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class QnaABoardService {
    @Autowired
    private QnaABoardDAO dao;

    @Autowired
    private QnaABoardImageDAO image;

    public QnaABoard create(QnaABoard vo) {
        return dao.save(vo);
    }

    public QnaABoardImage createImg(QnaABoardImage vo){
        return image.save(vo);
    }

    public QnaABoard view(int code){
        return dao.findById(code).orElse(null);
    }

    public QnaABoardImage viewImg(QnaQBoardImage vo){
        return image.findById(vo.getQnaQImgCode()).orElse(null);
    }

    public QnaABoard update(QnaABoard vo){
        if(dao.existsById(vo.getQnaACode())){
            return dao.save(vo);
        }
        return null;
    }

    public void deleteImg(int code){
         image.deleteById(code);
    }

}
