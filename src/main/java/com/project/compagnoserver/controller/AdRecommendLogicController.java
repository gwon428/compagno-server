package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AdRecommendLogic;
import com.project.compagnoserver.domain.ProductBoard.ProductBoard;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.AdRecommendLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class AdRecommendLogicController {

    @Autowired
    private AdRecommendLogicService logicService;
    @GetMapping("/public/logic-point/{userId}")
    public ResponseEntity<List<AdRecommendLogic>> getCurrentPoint (@PathVariable(name = "userId") String userId){

        List<AdRecommendLogic> list = logicService.getCurrentPoint(userId);
        log.info("point list : " + list);
        return ResponseEntity.ok(list);


    }

    // 상품 가져오기
    @GetMapping("/public/product-board/reviews")
    public ResponseEntity<List<ProductBoard>> getReviews(){
        List<ProductBoard> reviews =  logicService.getReviews();
        return reviews!=null ?  ResponseEntity.ok(reviews) : null;
    }
}
