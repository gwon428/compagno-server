package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetFaq;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetDAO;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetFaqDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterPetService {

    @Autowired
    private RegisterPetDAO dao;

    @Autowired
    private RegisterPetFaqDAO faqDao;

    // 대행기관 전체 보기
    public List<RegisterPet> instList() {
        return dao.findAll();
    }

    // faq 등록
    public void faqInsert(RegisterPetFaq faq) {
        faqDao.save(faq);
    }

    // faq 전체 보기
    public List<RegisterPetFaq> faqSelect() {
        return faqDao.findAll();
    }

    // faq 한 개 보기
    public RegisterPetFaq faqSelect(int faqCode) {
        return faqDao.findById(faqCode).orElse(null);
    }

    // faq 수정
    public void faqUpdate(RegisterPetFaq faq) {
        if(faqDao.existsById(faq.getRegiFaqCode())) {
            faqDao.save(faq);
        }
    }

    // faq 삭제
    public void faqDelete(int faqCode) {
        if(faqDao.existsById(faqCode)) {
            faqDao.deleteById(faqCode);
        }
    }

//    // faq 공개글만 조회
//    public List<RegisterPetFaq> getPublicFaq() {
//        return faqDao.findByregiFaqStatus("Y");
//    }
}
