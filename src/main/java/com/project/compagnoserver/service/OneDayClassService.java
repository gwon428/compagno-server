package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import com.project.compagnoserver.repo.OneDayClass.ClassBoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OneDayClassService {

    @Autowired
    private ClassBoardDAO dao;

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
    public void delete(int odcCode){
        if (dao.existsById(odcCode)){
            dao.deleteById(odcCode);
        };
    }
}
