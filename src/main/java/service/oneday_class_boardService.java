package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.oneday_class_boardDAO;

@Service
public class oneday_class_boardService {

    @Autowired
    private oneday_class_boardDAO dao;
}
