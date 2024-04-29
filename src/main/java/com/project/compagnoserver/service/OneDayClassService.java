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

    // 클래스 등록
    public void insert(ClassBoard vo){
        dao.save(vo);
    }
    //                                  객체에다가 저장
    
    // 클래스 사람들 조회
    public List<ClassBoard> viewAll() {
        return dao.findAll();
    }


    // 클래스 코드로 1명 찾기
    public ClassBoard view(int odcCode){
    return dao.findById(odcCode).orElse(null);
    }

    // 클래스 수정
    public void update(ClassBoard vo){
       if (dao.existsById(vo.getOdcCode())){
            dao.save(vo);
       };
    }

    // 클래스 삭제
    public void delete(int odcCode){
        if (dao.existsById(odcCode)){
            dao.deleteById(odcCode);
        };
    }
}
