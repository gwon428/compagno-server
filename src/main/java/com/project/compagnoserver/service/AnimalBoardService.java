package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.AnimalBoard;
import com.project.compagnoserver.repo.AnimalBoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalBoardService {

    @Autowired
    private AnimalBoardDAO boardDAO;

    // 게시글 작성
    public AnimalBoard write(AnimalBoard vo){
        return boardDAO.save(vo);
    }
}
