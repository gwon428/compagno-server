package com.project.compagnoserver.controller;

import com.project.compagnoserver.service.AnimalBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
public class AnimalBoardController {

    @Autowired
    private AnimalBoardService animalBoardService;
}
