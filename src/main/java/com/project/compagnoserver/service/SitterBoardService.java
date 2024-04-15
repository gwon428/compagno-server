package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.SitterBoard.SitterBoard;
import com.project.compagnoserver.domain.SitterBoard.SitterBoardImage;
import com.project.compagnoserver.repo.SitterBoard.SitterBoardDAO;
import com.project.compagnoserver.repo.SitterBoard.SitterBoardImageDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitterBoardService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private SitterBoardDAO sitterBoardDAO;

    @Autowired
    private SitterBoardImageDAO sitterBoardImageDAO;

    // 등록
    public SitterBoard sitterCreate(SitterBoard sitter) {
        return sitterBoardDAO.save(sitter);
    }
    public SitterBoardImage sitterCreateImg(SitterBoardImage sitterImg) {
        return sitterBoardImageDAO.save(sitterImg);
    }

    // 전체보기
    public List<SitterBoard> sitterSelectAll() {
        return sitterBoardDAO.findAll();
    }

    // 상세보기
    public SitterBoard sitterSelect(int sitterBoardCode) {
        return sitterBoardDAO.findById(sitterBoardCode).orElse(null);
    }
//    public List<SitterBoardImage> sitterSelectImg(int sitterImgCode) {
//    }

    // 수정
    public void sitterUpdate(SitterBoard sitter) {
        if(sitterBoardDAO.existsById(sitter.getSitterBoardCode())) {
            sitterBoardDAO.save(sitter);
        }
    }
    public SitterBoardImage sitterUpdateImg(SitterBoardImage sitterImg) {
        return sitterBoardImageDAO.save(sitterImg);
    }

    // 삭제
    public void sitterDelete(int sitterBoardCode) {
        if(sitterBoardDAO.existsById(sitterBoardCode)){
            sitterBoardDAO.deleteById(sitterBoardCode);
        }
    }
}
