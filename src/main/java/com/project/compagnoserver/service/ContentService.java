package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Parsing.ContentsLocationParsing;
import com.project.compagnoserver.domain.Parsing.ContentsLocationParsingDTO;
import com.project.compagnoserver.domain.Parsing.Parsing;
import com.project.compagnoserver.domain.Parsing.QContentsLocationParsing;
import com.project.compagnoserver.repo.Parsing.ContentsLocationParsingDAO;
import com.project.compagnoserver.repo.Parsing.ParsingDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

@Service @Slf4j
public class ContentService {

    @Autowired
    private ParsingDAO dao;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private ContentsLocationParsingDAO locationdao;

    private final QContentsLocationParsing qContentsLocationParsing = QContentsLocationParsing.contentsLocationParsing;

        private String fileName = "location.xls";

        @Autowired
        private ContentsLocationParsingDAO contentsLocationParsingDAO;

        // location.xls 파싱
        public void saveToDb() {

            try {
                List<Map<Object, Object>> locationData = readExcel(fileName);
                for(Map<Object, Object> rowMap : locationData) {
                    ContentsLocationParsing location = new ContentsLocationParsing();
                    location.setLocationName((String) rowMap.get("주소명"));
                    String parentCodeString = (String) rowMap.get("부모코드");
                    if (!parentCodeString.isEmpty()) {
                        location.setLocationParentCode(Integer.parseInt(parentCodeString));
                    } else {
                        // 부모코드가 비어 있거나 null인 경우에 대한 처리
                        location.setLocationParentCode(00); // 또는 다른 적절한 값을 설정할 수 있습니다.
                    }

                    // db 저장
                    locationdao.save(location);
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


    public List<Parsing> findAll(){
        return dao.findAll();
    }
    public List<Parsing> findByMainCateCode(int code){
        return dao.findByMainCateCode(code);
    }

    public List<Parsing> findBySubCateCode(int code) {
        return dao.findBySubCateCode(code);
    }

    //
    public List<Parsing> findByMainReg(int code) {
        return dao.findByMainReg(code);
    }

    public Optional<Parsing> findById(int code){
        return dao.findById(code);
    }

    public List<Parsing> findByMainCateReg(int code, int reg){
        return dao.findByMainCateReg(code, reg);
    }

    //===========================location============================
    // 시도 조회
    public List<ContentsLocationParsing> getProvinces(){
        return queryFactory.selectFrom(qContentsLocationParsing)
                .where(qContentsLocationParsing.locationCode.eq(0))
                .fetch();
    }

    // 시도별 시군구 조회
    public List<ContentsLocationParsing> getDistricts(int code){
        return queryFactory.selectFrom(qContentsLocationParsing)
                .where(qContentsLocationParsing.locationParentCode.eq(code))
                .fetch();
    }
}
