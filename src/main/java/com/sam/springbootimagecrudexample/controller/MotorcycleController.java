package com.sam.springbootimagecrudexample.controller;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.service.MotorcycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    public MotorcycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @GetMapping("/motorcycle/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("motorcycle",motorcycleService.findById(Long.valueOf(id)));
        return "motorcycle/show";
    }

    @GetMapping("motorcycle/new")
    public String newMotorcycle(Model model){
        model.addAttribute("motorcycle",new Motorcycle());
        return "motorcycle/motorcycleform";
    }

    @GetMapping("motorcycle/{id}/update")
    public String updateMotorcycle(@PathVariable  String id, Model model){
        model.addAttribute("motorcycle",motorcycleService.findById(Long.valueOf(id)));
        return "motorcycle/motorcycleform";
    }

    @PostMapping("motorcycle")
    public String saveOrUpdate(@Valid @ModelAttribute Motorcycle motorcycle, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(System.out::println);
            return "motorcycle/motorcycleform";
        }

        Motorcycle savedMotorcycle = motorcycleService.save(motorcycle);

        return "redirect:/motorcycle/"+savedMotorcycle.getId()+"/show";
    }

    @GetMapping("motorcycle/{id}/delete")
    public String deleteById(@PathVariable String id){

        motorcycleService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
