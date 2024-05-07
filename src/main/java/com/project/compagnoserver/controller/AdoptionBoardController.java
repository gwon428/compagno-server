package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoard;
import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoardDTO;
import com.project.compagnoserver.domain.AdoptionBoard.AdoptionBoardImage;
import com.project.compagnoserver.domain.AdoptionBoard.QAdoptionBoard;
import com.project.compagnoserver.service.AdoptionBoardService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class AdoptionBoardController {

    @Autowired
    private AdoptionBoardService service;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 추가
    @PostMapping("/adoptionBoard")
    public ResponseEntity<AdoptionBoard> create(AdoptionBoardDTO dto) throws IOException {

        //    private int adopViewCount;
        //    private String adopAnimalImage;
        //    private List<MultipartFile> images;
        //    private List<String> image;


        AdoptionBoard result =
                AdoptionBoard.builder()
                        .userId(dto.getUserId())
                        .userImg(dto.getUserImg())
                        .userNickname(dto.getUserNickname())
                        .userPhone(dto.getUserPhone())
                        .adopAnimalKind(dto.getAdopAnimalKind())
                        .adopAnimalColor(dto.getAdopAnimalColor())
                        .adopAnimalFindplace(dto.getAdopAnimalFindplace())
                        .adopAnimalGender(dto.getAdopAnimalGender())
                        .adopAnimalNeuter(dto.getAdopAnimalNeuter())
                        .adopAnimalAge(dto.getAdopAnimalAge())
                        .adopAnimalKg(dto.getAdopAnimalKg())
                        .adopAnimalFeature(dto.getAdopAnimalFeature())
                        .adopCenterName(dto.getAdopCenterName())
                        .adopCenterPhone(dto.getAdopCenterPhone())
                        .build();


        if(dto.getImages()!=null){
            for(MultipartFile file : dto.getImages()){
                if(!file.getOriginalFilename().equals("")){
                    AdoptionBoardImage images = new AdoptionBoardImage();

                    String fileName = file.getOriginalFilename();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uploadPath + File.separator + "adoptionBoard" + File.separator + uuid + "_" + fileName;
                    Path savePath = Paths.get(saveName);
                    file.transferTo(savePath);

                    if(result.getAdopAnimalImage()==null){
                        result.setAdopAnimalImage(saveName);
                    }

                    images.setAdopImage(saveName);
                    images.setAdopBoardCode(result);

                    service.createImg(images);
                }
            }
        }

//        result.setAdopAnimalImage(mainImage.getFirst());
        service.create(result);
        return result!=null?
                ResponseEntity.status(HttpStatus.CREATED).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    // 전체보기(페이징, 검색, 정렬)
    // 검색 : 종류(전체, 강아지, 고양이, 그외) / 성별 /  입양 동물 중성화 유무 / 센터 이름 / 입양 동물 발견 장소
    // 정렬 : 작성일 순(오래된 순/최신순) / 조회수 순(높&낮)
    @GetMapping("/public/adoptionBoard")
    public ResponseEntity<Page<AdoptionBoard>> viewAll(@RequestParam(name="page", defaultValue = "1")int page,
                                                       @RequestParam(name="adopAnimalKind",required = false)String adopAnimalKind,
                                                       @RequestParam(name="adopAnimalGender", required = false)String adopAnimalGender,
                                                       @RequestParam(name="adopAnimalNeuter", required = false)String adopAnimalNeuter,
                                                       @RequestParam(name="adopAnimalFindplace", required = false)String adopAnimalFindplace,
                                                       @RequestParam(name="adopCenterName", required = false)String adopCenterName,
                                                       @RequestParam(name="sort", defaultValue = "0")int sortNum){
        QAdoptionBoard qAdoptionBoard = QAdoptionBoard.adoptionBoard;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = null;
        if(adopAnimalKind!=null){
            expression = qAdoptionBoard.adopAnimalKind.contains(adopAnimalKind);
            builder.and(expression);
        }

        if(adopAnimalGender!=null){
            expression = qAdoptionBoard.adopAnimalGender.contains(adopAnimalGender);
            builder.and(expression);
        }
        if(adopAnimalNeuter!=null){
            expression = qAdoptionBoard.adopAnimalNeuter.contains(adopAnimalNeuter);
            builder.and(expression);
        }
        if(adopAnimalFindplace!=null){
            expression = qAdoptionBoard.adopAnimalFindplace.contains(adopAnimalFindplace);
            builder.and(expression);
        }
        if(adopCenterName!=null){
            expression = qAdoptionBoard.adopCenterName.contains(adopCenterName);
            builder.and(expression);
        }
        Pageable pageable = PageRequest.of(page-1, 12);
        Sort aRegiDateD = Sort.by("adopBoardCode").descending();
        Sort aRegiDate = Sort.by("adopRegiDate");
        if(sortNum==0){
            pageable = PageRequest.of(page-1, 12, aRegiDateD);
        }
        if(sortNum==1){
            pageable = PageRequest.of(page-1, 12, aRegiDate);
        }

//        Sort aViewCount = Sort.by("adopViewCount");
//        Sort aViewCountD = Sort.by("adopViewCount").descending();
//
//        if(sortNum==2){
//            pageable = PageRequest.of(page-1, 12, aViewCount);
//        }
//        if(sortNum==3){
//            pageable = PageRequest.of(page-1, 12, aViewCountD);
//        }

        return ResponseEntity.ok(service.viewAll(pageable, builder));
    }

    // 하나 보기
    @GetMapping("/public/adoptionBoard/{adopBoardCode}")
    public ResponseEntity<AdoptionBoard> view(@PathVariable(name="adopBoardCode")int adopBoardCode){
        AdoptionBoard adoption = service.view(adopBoardCode);
        return ResponseEntity.status(HttpStatus.OK).body(adoption);
    }

    // 수정(사진도 함께)
    @PutMapping("/adoptionBoard")
    public ResponseEntity<AdoptionBoard> update(AdoptionBoardDTO dto) throws IOException {
        List<AdoptionBoardImage> list = service.findByImg(dto.getAdopBoardCode());
        List<String> mainImage = new ArrayList<>();

        AdoptionBoard adop = AdoptionBoard.builder()
                .userId(dto.getUserId())
                .userImg(dto.getUserImg())
                .userPhone(dto.getUserPhone())
                .userNickname(dto.getUserNickname())
                .adopBoardCode(dto.getAdopBoardCode())
                .adopAnimalKind(dto.getAdopAnimalKind())
                .adopAnimalColor(dto.getAdopAnimalColor())
                .adopAnimalFindplace(dto.getAdopAnimalFindplace())
                .adopAnimalGender(dto.getAdopAnimalGender())
                .adopAnimalNeuter(dto.getAdopAnimalNeuter())
                .adopAnimalAge(dto.getAdopAnimalAge())
                .adopAnimalKg(dto.getAdopAnimalKg())
                .adopAnimalFeature(dto.getAdopAnimalFeature())
                .adopCenterName(dto.getAdopCenterName())
                .adopCenterPhone(dto.getAdopCenterPhone())
                .adopRegiDate(dto.getAdopRegiDate())
                .build();

        for(AdoptionBoardImage image : list){
            if(dto.getImage()!=null && !dto.getImage().contains(image.getAdopImage())||dto.getImage()==null){
                File file = new File(image.getAdopImage());
                file.delete();
                service.delImg(image.getAdopImageCode());
            }else{
                mainImage.add(image.getAdopImage());
            }
        }

        if(dto.getImages()!=null){
            for(AdoptionBoardImage image:list){
                File file = new File(image.getAdopImage());
                file.delete();
                service.delImg(image.getAdopImageCode());
                mainImage.remove(0);
            }
            for(MultipartFile file : dto.getImages()){
                String fileName =  file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();

                String saveName = uploadPath + File.separator + "adoptionBoard" + File.separator + uuid + "_" + fileName;
                Path savePath = Paths.get(saveName);
                file.transferTo(savePath);

                service.createImg(AdoptionBoardImage.builder()
                                .adopImage(saveName)
                                .adopBoardCode(AdoptionBoard.builder().adopBoardCode(dto.getAdopBoardCode()).build())
                        .build());
                mainImage.add(saveName);
            }
        }
        adop.setAdopAnimalImage(mainImage.getFirst());
        service.create(adop);
        return ResponseEntity.ok().build();
    }


    // 삭제
    @DeleteMapping("/adoptionBoard/{adopBoardCode}")
    public ResponseEntity<AdoptionBoard> delete(@PathVariable(name="adopBoardCode") int adopBoardCode){
        AdoptionBoard vo = service.view(adopBoardCode);
        if(vo!=null){
            service.deleteImg(adopBoardCode);
            service.delete(adopBoardCode);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
