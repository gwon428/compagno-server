package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.AnimalBoard;
import com.project.compagnoserver.domain.AnimalBoardImage;
import com.project.compagnoserver.repo.AnimalBoardDAO;
import com.project.compagnoserver.repo.AnimalBoardImageDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AnimalBoardService {

    @Autowired
    private AnimalBoardDAO animalBoardDAO;

    @Autowired
    private AnimalBoardImageDAO animalBoardImageDAO;

    // 자유게시판 글쓰기 - 1) 글쓰기
    public AnimalBoard write(AnimalBoard board){
        return animalBoardDAO.save(board);
    }

    // 자유게시판 글쓰기 - 2) 이미지 저장
    public AnimalBoardImage writeImages(AnimalBoardImage images){
        return animalBoardImageDAO.save(images);
    }

    // 자유게시판 글쓰기 -3) 동영상 저장

    // 자유게시판 전체보기
    public List<AnimalBoard> viewAll(){
        return animalBoardDAO.findAll();
    }

    // 자유게시판 - 글 한개보기 = 상세페이지
    public AnimalBoard viewDetail(int animalBoardCode){
        return animalBoardDAO.findById(animalBoardCode).orElse(null);
    }

    // 자유게시판 - 글 수정
    public AnimalBoard boardUpdate(AnimalBoard board){
        return animalBoardDAO.existsById(board.getAnimalBoardCode()) ? animalBoardDAO.save(board) : null;
    }

    // 자유게시판 - 글 삭제
}
