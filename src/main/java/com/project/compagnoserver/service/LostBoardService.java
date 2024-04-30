package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.LostBoard.LostBoard;
import com.project.compagnoserver.domain.LostBoard.LostBoardImage;
import com.project.compagnoserver.repo.LostBoard.LostBoardDAO;
import com.project.compagnoserver.repo.LostBoard.LostBoardImageDAO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostBoardService {

    @Autowired
    private LostBoardDAO boardDAO;

    @Autowired
    private LostBoardImageDAO imagesDAO;

    // 추가
    public LostBoard create(LostBoard vo) {return boardDAO.save(vo);}
    public LostBoardImage createImages(LostBoardImage images) {return imagesDAO.save(images);}

    // 보기 -----------------------------------------------------------
    // 전체 보기 ----------------------------
    public Page<LostBoard> viewAll(Pageable pageable, BooleanBuilder builder) {return boardDAO.findAll(builder, pageable);}


    // 하나 보기 ----------------------------
    public LostBoard view(int lostBoardCode) {return boardDAO.findById(lostBoardCode).orElse(null);}

    // 검색 보기 -----------------------------
    public Page<LostBoard> viewBySearch(Pageable pageable, BooleanBuilder builder){
        return boardDAO.findAll(builder, pageable);
    }
    
    // 정렬 보기
//    public Page<LostBoard> viewBySort(Pageable pageable){
//        return boardDAO.findAll(pageable);
//    }


    // 수정 ---------------------------------------------------------
    public LostBoard update(LostBoard vo) {
//        if(boardDAO.existsById(vo.getLostBoardCode())){
//            return boardDAO.save(vo);
//        }
//        return null;
        return boardDAO.save(vo);
    }
    public LostBoardImage update(LostBoardImage image){
        return imagesDAO.save(image);
    }


    // 삭제 ---------------------------------------------------------------
    public void delete(int lostBoardCode){
        LostBoard vo = boardDAO.findById(lostBoardCode).orElse(null);
        if(vo!=null){
            boardDAO.delete(vo);
        }
    }

    public void imageDelete(int lostBoardCode){
        List<LostBoardImage> list = imagesDAO.findByCode(lostBoardCode);
        if(list!=null){
            imagesDAO.deleteAll(list);
        }

    }

}
