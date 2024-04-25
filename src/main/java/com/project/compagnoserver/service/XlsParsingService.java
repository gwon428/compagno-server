package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetFaq;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetDAO;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetFaqDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class XlsParsingService {

    private String instsFileName = "동물등록 대행기관.xls";

    @Autowired
    private RegisterPetDAO registerPetDAO;

    public void saveToDb() {

        try {
            // 대행기관 데이터
            List<Map<Object, Object>> instsData = readExcel(instsFileName);
            for(Map<Object, Object> rowMap : instsData) {
                RegisterPet regiPet = new RegisterPet();
                regiPet.setRegiInstName((String) rowMap.get("업체명"));
                regiPet.setRegiInstOwner((String) rowMap.get("대표자명"));
                regiPet.setRegiInstPhone((String) rowMap.get("전화번호"));
                regiPet.setRegiInstAddr((String) rowMap.get("주소"));

                // db 저장
                registerPetDAO.save(regiPet);
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

        String[] columns = {"업체명", "대표자명", "전화번호", "주소"};
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
}
