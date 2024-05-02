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

import java.util.List;

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
        return dao.findByqnaQCode(code);
    }

    public List<QnaABoardImage> viewImg(int code){
        return image.findByQnaACode(code);
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

    public QnaABoard delete(int code) {
        QnaABoard target = dao.findById(code).orElse(null);
        if(target != null){
            dao.delete(target);
        }
        return target;
    }
}
