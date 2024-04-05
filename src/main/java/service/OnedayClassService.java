package service;

import com.querydsl.core.BooleanBuilder;
import domain.OnedayClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import repo.OnedayClassDAO;

@Service
public class OnedayClassService {

    @Autowired
    private OnedayClassDAO dao;

    public Page<OnedayClass> viewAll(Pageable pageable, BooleanBuilder builder){
        return dao.findAll(builder, pageable);
    }
}
