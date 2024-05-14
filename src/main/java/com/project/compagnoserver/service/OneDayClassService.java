package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.domain.OneDayClass.ClassBoardMainImage;
import com.project.compagnoserver.repo.OneDayClass.ClassBoardDAO;
import com.project.compagnoserver.repo.OneDayClass.ClassBoardMainImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OneDayClassService {

    @Autowired
    private ClassBoardDAO dao;

    @Autowired
    private ClassBoardMainImageDAO img;

    // 클래스 보드 이미지 클래스 관련 로직

    // 이미지 저장
    public ClassBoardMainImage createImg(ClassBoardMainImage vo){
        return img.save(vo);
    }
    // 이미지 조회 (메인이미지관련 정보 조회)
    public ClassBoardMainImage viewImg(int odcCode){
        return img.findByCode(odcCode);
    }

    // 이미지 수정
    public ClassBoardMainImage updateImages(ClassBoardMainImage vo){
        if (img.existsById(vo.getOdcImageCode())){
            return img.save(vo);
        }
        return null;
    }

    // 이미지 삭제
    public void deleteImg(int odcCode){
        img.deleteById(odcCode);
    }


    // ============================================================================

    // 원데이 클래스 등록 insert
    public ClassBoard insert(ClassBoard vo){
        return dao.save(vo);
    }
    //        객체에다가 저장
    
    // 원데이 클래스 등록 List 목록 전체
    public List<ClassBoard> viewAll() {
        return dao.findAll();
    }

    // 원데이클래스 코드로 하나 조회 => Detail
    public ClassBoard view(int odcCode){
    return dao.findById(odcCode).orElse(null);
    }

    // 등록된 원데이 클래스 수정
    public ClassBoard update(ClassBoard vo){
       if (dao.existsById(vo.getOdcCode())){
          return dao.save(vo);
       }
       return null;
    };

    // 등록된 원데이 클래스 삭제
    public ClassBoard delete(int odcCode){
        if (dao.existsById(odcCode)){
            dao.deleteById(odcCode);
        };
        return null;
    }
}
