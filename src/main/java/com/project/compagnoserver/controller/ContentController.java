package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Parsing.ContentsLocationParsing;
import com.project.compagnoserver.domain.Parsing.ContentsLocationParsingDTO;
import com.project.compagnoserver.domain.Parsing.Parsing;
import com.project.compagnoserver.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compagno/public/content")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class ContentController {

    @Autowired
    private ContentService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    // 전체보기
    @GetMapping("/view")
    public ResponseEntity<List<Parsing>> view(){
        List<Parsing> list = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 메인카테고리별
    @GetMapping("/view/mainCate/{code}")
    public ResponseEntity<List<Parsing>> viewMain(@PathVariable(name="code") int code){
        List<Parsing> list = service.findByMainCateCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 서브카테고리별
    @GetMapping("/view/subCate/{code}")
    public ResponseEntity<List<Parsing>> viewSub(@PathVariable(name="code") int code){
        List<Parsing> list = service.findBySubCateCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //v지역별
    @GetMapping("/view/mainReg/{code}")
    public ResponseEntity<List<Parsing>> viewreg(@PathVariable(name="code")int code){
        List<Parsing> list = service.findByMainReg(code);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 한개씩 보기(상세정보)
    @GetMapping("/view/num/{code}")
    public ResponseEntity<Optional<Parsing>> view(@PathVariable(name="code") int code){
        Optional<Parsing> vo = service.findById(code);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    @GetMapping("/view/{code}/{reg}")
    public ResponseEntity<List<Parsing>> viewMainReg(@PathVariable(name="code") int code, @PathVariable(name="reg") int reg){
        List<Parsing> list = service.findByMainCateReg(code, reg);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/upload")
    public ResponseEntity saveToDb(){
        service.saveToDb();
        return ResponseEntity.ok().build();
    }

    // 시도 조회
    @GetMapping("/location/province")
    public ResponseEntity<List<ContentsLocationParsing>> viewProvince(){
        return ResponseEntity.ok(service.getProvinces());
    }

    // 시도 선택에 따른 시군구 조회
    @GetMapping("/location/district/{code}")
    public ResponseEntity<List<ContentsLocationParsingDTO>> viewDistrict(@PathVariable(name="code") int code){
        List<ContentsLocationParsing> districts = service.getDistricts(code);
        List<ContentsLocationParsingDTO> districtsDTO = new ArrayList<>();

        for(ContentsLocationParsing district : districts){
            ContentsLocationParsingDTO dto = ContentsLocationParsingDTO.builder()
                    .locationCode(district.getLocationCode())
                    .locationName(district.getLocationName())
                    .build();
            districtsDTO.add(dto);
        }
        return ResponseEntity.ok(districtsDTO);
    }
}
