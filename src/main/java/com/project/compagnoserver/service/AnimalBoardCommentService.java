package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoardComment;
import com.project.compagnoserver.domain.Animal.AnimalBoardCommentDTO;
import com.project.compagnoserver.domain.Animal.QAnimalBoardComment;
import com.project.compagnoserver.repo.Animal.AnimalBoardCommentDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalBoardCommentService {

    @Autowired
    private AnimalBoardCommentDAO animalBoardCommentDAO;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QAnimalBoardComment qAnimalBoardComment = QAnimalBoardComment.animalBoardComment;

    // 게시글에 댓글 쓰기
    public AnimalBoardComment writeComment(AnimalBoardComment vo){
        return animalBoardCommentDAO.save(vo);
    }

    // 상위댓글 불러오기  boardCode=? parentCode =0
    public List<AnimalBoardComment> topLevelComments (int animalBoardCode){
        return jpaQueryFactory.selectFrom(qAnimalBoardComment)
                .where(qAnimalBoardComment.animalParentCode.eq(0))
                .where(qAnimalBoardComment.animalBoard.animalBoardCode.eq(animalBoardCode))
                .fetch();
    }
    // 하위댓글 불러오기 boardCode =? parentCode = ?
    public List<AnimalBoardComment> bottomLevelComments(int animalBoardCode, int parentCode){
        return jpaQueryFactory.selectFrom(qAnimalBoardComment)
                .where(qAnimalBoardComment.animalBoard.animalBoardCode.eq(animalBoardCode))
                .where(qAnimalBoardComment.animalParentCode.eq(parentCode))
                .fetch();
    }
}
