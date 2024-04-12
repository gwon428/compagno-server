package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.RegisterPet;
import com.project.compagnoserver.repo.RegisterPetDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterPetService {

    @Autowired
    private RegisterPetDAO dao;

    // 전체 보기
    public List<RegisterPet> select() {
        return dao.findAll();
    }

}
