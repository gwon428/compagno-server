package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoardComment;
import com.project.compagnoserver.domain.Animal.AnimalBoardCommentDTO;
import com.project.compagnoserver.repo.Animal.AnimalBoardCommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalBoardCommentService {

    @Autowired
    private AnimalBoardCommentDAO animalBoardCommentDAO;

    // 게시글에 댓글 쓰기
    public AnimalBoardComment writeComment(AnimalBoardComment vo){
        return animalBoardCommentDAO.save(vo);
    }
}
