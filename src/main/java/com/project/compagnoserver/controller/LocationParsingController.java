package com.project.compagnoserver.controller;

import com.project.compagnoserver.service.LocationParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compagno/*")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class LocationParsingController {
    @Autowired
    private LocationParsingService service;

    @GetMapping("public/upload")
    public ResponseEntity saveToDb() {
        service.saveToDb();
        return ResponseEntity.ok().build();
    }
}
