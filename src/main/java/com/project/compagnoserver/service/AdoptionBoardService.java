package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoard;
import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoardImage;
import com.project.compagnoserver.repo.AdoptionBoard.AdoptionBoardDAO;
import com.project.compagnoserver.repo.AdoptionBoard.AdoptionBoardImageDAO;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdoptionBoardService {

    @Autowired
    private AdoptionBoardDAO dao;

    @Autowired
    private AdoptionBoardImageDAO imgDao;

    // 추가
    public AdoptionBoard create(AdoptionBoard vo) {return dao.save(vo);}
    public AdoptionBoardImage createImg(AdoptionBoardImage image) {return imgDao.save(image);}

    /* views Counting*/
    public void updateView(int adopBoardCode){
        dao.updateView(adopBoardCode);
    }

    // 전체 보기
    public Page<AdoptionBoard> viewAll(Pageable pagealbe, BooleanBuilder builder){return dao.findAll(builder, pagealbe);}

    // 하나 보기 (검색, 정렬, 페이징 포함)
    public AdoptionBoard view(int adopBoardCode){return dao.findById(adopBoardCode).orElse(null);}

    // 이미지 테이블에서 adopBoardCode로 찾기
    public List<AdoptionBoardImage> findByImg(int adopBoardCode){return imgDao.findByCode(adopBoardCode);}


    // 수정
    public AdoptionBoard update(AdoptionBoard vo){return dao.save(vo);}
    public AdoptionBoardImage updateImg(AdoptionBoardImage image){return imgDao.save(image);}

    // 삭제
    public void delete(int adopBoardCode) {
        AdoptionBoard vo = dao.findById(adopBoardCode).orElse(null);
        if (vo != null) {
            dao.delete((vo));
        }
    }
    public void deleteImg(int adopBoardCode){
        List<AdoptionBoardImage> list = imgDao.findByCode(adopBoardCode);
        if(list!=null){
            imgDao.deleteAll(list);
        }
    }
    public void delImg(int adopImageCode){
        imgDao.deleteById(adopImageCode);
    }

}
