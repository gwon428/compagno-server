package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.RegisterPet.QRegisterPetLocation;
import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetFaq;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetLocation;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetDAO;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetFaqDAO;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetLocationDAO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterPetService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private RegisterPetDAO dao;

    @Autowired
    private RegisterPetFaqDAO faqDao;

    @Autowired
    private RegisterPetLocationDAO registerPetLocationDAO;
    private final QRegisterPetLocation qRegisterPetLocation = QRegisterPetLocation.registerPetLocation;


    // 대행기관 전체 보기
    public Page<RegisterPet> instList(Pageable pageable) {
        return dao.findAll(pageable);

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



//    ========================================= FAQ xls 파싱 =========================================
private String faqFileName = "동물등록 FAQ.xls";

    public void saveToDb() {
        try {
            List<Map<Object, Object>> faqData = readExcel(faqFileName);
            for(Map<Object, Object> rowMap : faqData) {
                RegisterPetFaq regiPetFaq = new RegisterPetFaq();
                regiPetFaq.setRegiFaqQuestion((String) rowMap.get("질문"));
                regiPetFaq.setRegiFaqAnswer((String) rowMap.get("답변"));
                faqDao.save(regiPetFaq); // db 저장
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Map<Object, Object>> readExcel(String fileName) throws IOException {
        List<Map<Object, Object>> list = new ArrayList<>();

        FileInputStream fis = new FileInputStream(new File(fileName));
        Workbook workbook = new HSSFWorkbook(fis);
        if(workbook != null) {
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();

            for(int i=0; i<=rows; i++) {
                Row row = sheet.getRow(i);
                if(row != null) {
                    int cells = row.getPhysicalNumberOfCells();
                    list.add(getCell(row, cells));
                }
            }
        }

        return list;
    }

    public static Map<Object, Object> getCell(Row row, int cells) {
        Map<Object, Object> map = new HashMap<>();

        String[] columns = {"질문", "답변"};
        for(int i=0; i<cells; i++) {
            if(i>= columns.length) {
                break;
            }
            Cell cell = row.getCell(i);
            if(cell != null) {
                switch(cell.getCellType()) {
                    case STRING:
                        map.put(columns[i], cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        map.put(columns[i], cell.getNumericCellValue());
                        break;
                    default:
                        map.put(columns[i], "");
                        break;
                }
            }
        }

        return map;
    }



// ====================================== Location ======================================

    // 시도 조회
    public List<RegisterPetLocation> registerGetProvinces() {
    return queryFactory.selectFrom(qRegisterPetLocation)
            .where(qRegisterPetLocation.locationParentCode.eq(0))
            .fetch();
    }

    // 시도별 시군구 조회
    public List<RegisterPetLocation> registerGetDistricts(int code) {
        return queryFactory.selectFrom(qRegisterPetLocation)
                .where(qRegisterPetLocation.locationParentCode.eq(code))
                .fetch();
    }


}
