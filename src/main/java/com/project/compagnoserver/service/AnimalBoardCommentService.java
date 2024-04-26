package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoardComment;
import com.project.compagnoserver.domain.Animal.AnimalBoardCommentDTO;
import com.project.compagnoserver.domain.Animal.QAnimalBoardComment;
import com.project.compagnoserver.repo.Animal.AnimalBoardCommentDAO;
import com.querydsl.jpa.impl.JPAQuery;
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

    // 게시글 댓글 수정
//    public AnimalBoardComment updateComment(AnimalBoardComment vo){
//        if(animalBoardCommentDAO.existsById(vo.getAnimalCommentCode())){
//            return animalBoardCommentDAO.save(vo);
//        }
//        return null;
//
//    }
    // 게시글 댓글 삭제
    public void deleteComment(int commentCode){
        // parentCode가 존재하는 자식댓글의 경우 개별적인 삭제가 가능
        animalBoardCommentDAO.deleteById(commentCode); // 그냥 개별적으로 지울때.
    }
    public void deleteParentChildren(int commentCode){ // 부모의 commentCode
        // parentCode가 0 인 부모댓글의 경우, 자식댓글도 함께 삭제를 해 줘야 함.
        //자식 먼저 삭제
        animalBoardCommentDAO.deleteChildrenComment(commentCode);
        // 그후 부모 삭제
        animalBoardCommentDAO.deleteById(commentCode);
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
