package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Parsing.LocationParsing;
import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.project.compagnoserver.repo.Parsing.LocationParsingDAO;
import com.project.compagnoserver.repo.RegisterPet.RegisterPetDAO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationParsingService {

    private String fileName = "location.xls";

    @Autowired
    private LocationParsingDAO locationParsingDAO;

    public void saveToDb() {

        try {
            List<Map<Object, Object>> locationData = readExcel(fileName);
            for(Map<Object, Object> rowMap : locationData) {
                LocationParsing location = new LocationParsing();
                location.setLocationName((String) rowMap.get("주소명"));
                String parentCodeString = (String) rowMap.get("부모코드");
                if (!parentCodeString.isEmpty()) {
                    location.setLocationParentCode(Integer.parseInt(parentCodeString));
                } else {
                    // 부모코드가 비어 있거나 null인 경우에 대한 처리
                    location.setLocationParentCode(00); // 또는 다른 적절한 값을 설정할 수 있습니다.
                }

                // db 저장
                locationParsingDAO.save(location);
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

        String[] columns = {"주소명", "부모코드"};
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
