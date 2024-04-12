package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.AnimalBoard;
import com.project.compagnoserver.repo.AnimalBoardDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnimalBoardService {

    @Autowired
    private AnimalBoardDAO animalBoardDAO;

    // 자유게시판 글쓰기
    public AnimalBoard write(AnimalBoard board){
        return animalBoardDAO.save(board);
    }
}
