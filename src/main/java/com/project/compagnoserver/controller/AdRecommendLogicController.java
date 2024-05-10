package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Animal.AdRecommendLogic;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.service.AdRecommentLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class AdRecommendLogicController {

    @Autowired
    private AdRecommentLogicService logicService;
    @GetMapping("/public/logic-point")
    public ResponseEntity<List<AdRecommendLogic>> getCurrentPoint (User user){
        List<AdRecommendLogic> list = logicService.getCurrentPoint(user.getUserId());
        return ResponseEntity.ok().build();
    }
}
