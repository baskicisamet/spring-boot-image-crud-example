package com.sam.springbootimagecrudexample.controller;


import com.sam.springbootimagecrudexample.service.MotorcycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final MotorcycleService motorcycleService;

    public IndexController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){
        model.addAttribute("motorcycles",motorcycleService.getMotorcycles());

        return "index";
    }

}
