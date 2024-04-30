package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetFaq;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetLocation;
import com.project.compagnoserver.domain.RegisterPet.RegisterPetLocationDTO;
import com.project.compagnoserver.service.RegisterPetService;
import com.project.compagnoserver.service.XlsParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class RegisterPetController {

    @Autowired
    private RegisterPetService service;

    @Autowired
    private XlsParsingService xlsService;

    // 등록 대행기관 데이터 파싱
    @GetMapping("public/instsUpload")
    public ResponseEntity saveToDb() {
        xlsService.saveToDb();
        return ResponseEntity.ok().build();
    }

    // 대행기관 전체보기
    @GetMapping("public/register-pet")
    public ResponseEntity<Page<RegisterPet>> instList(@RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page-1, 10);

        Page<RegisterPet> list = service.instList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // faq 파싱
    @GetMapping("public/faqUpload")
    public ResponseEntity saveToDbFaq() {
        service.saveToDb();
        return ResponseEntity.ok().build();
    }

    // faq 등록
    @PostMapping("register-pet/faq")
    public ResponseEntity faqInsert(@RequestBody RegisterPetFaq faq) {
        service.faqInsert(faq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // faq 전체 보기
    @GetMapping("public/register-pet/faq")
    public ResponseEntity<List<RegisterPetFaq>> faqSelect() {
        List<RegisterPetFaq> list = service.faqSelect();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // faq 한 개 보기
    @GetMapping("public/register-pet/faq/{faqCode}")
    public ResponseEntity<RegisterPetFaq> faqSelect(@PathVariable("faqCode") int faqCode) {
        RegisterPetFaq faq = service.faqSelect(faqCode);
        return ResponseEntity.status(HttpStatus.OK).body(faq);
    }

    // faq 수정
    @PutMapping("register-pet/faq")
    public ResponseEntity faqUpdate(@RequestBody RegisterPetFaq faq) {
        service.faqUpdate(faq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // faq 삭제
    @DeleteMapping("register-pet/faq/{faqCode}")
    public ResponseEntity faqDelete(@PathVariable("faqCode") int faqCode) {
        service.faqDelete(faqCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    // faq 공개글만 조회
//    @GetMapping("register-pet/faq")
//    public ResponseEntity<List<RegisterPetFaq>> getPublicFaq() {
//        List<RegisterPetFaq> list = service.getPublicFaq();
//        return ResponseEntity.status(HttpStatus.OK).body(list);
//    }




// ====================================== Location ======================================

    // 시도 조회
    @GetMapping("public/register-pet/province")
    public ResponseEntity<List<RegisterPetLocation>> registerViewProvinces() {
        return ResponseEntity.ok(service.registerGetProvinces());
    }

    // 시도 선택에 따른 시군구 조회
    @GetMapping("public/register-pet/district/{code}")
    public ResponseEntity<List<RegisterPetLocationDTO>> viewDistrict(@PathVariable(name="code") int code) {
        List<RegisterPetLocation> districts = service.registerGetDistricts(code);
        List<RegisterPetLocationDTO> districtsDTO = new ArrayList<>();

        for(RegisterPetLocation district : districts) {
            RegisterPetLocationDTO dto = RegisterPetLocationDTO.builder()
                    .locationCode(district.getLocationCode())
                    .locationName(district.getLocationName())
                    .build();
            districtsDTO.add(dto);
        }
        return ResponseEntity.ok(districtsDTO);
    }


}
