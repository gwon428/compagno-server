package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import service.OnedayClassService;

@RestController
public class OnedayClassController {

    @Autowired
    private OnedayClassService service;


}
