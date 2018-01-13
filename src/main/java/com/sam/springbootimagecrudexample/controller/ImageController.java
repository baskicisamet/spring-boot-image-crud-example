package com.sam.springbootimagecrudexample.controller;

import com.sam.springbootimagecrudexample.domain.ImageFileModel;
import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.service.ImageService;
import com.sam.springbootimagecrudexample.service.MotorcycleService;
import com.sam.springbootimagecrudexample.validator.ImageValidator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final MotorcycleService motorcycleService;


    public ImageController(ImageService imageService, MotorcycleService motorcycleService) {
        this.imageService = imageService;
        this.motorcycleService = motorcycleService;
    }

    @GetMapping("motorcycle/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){


       ImageFileModel imageFileModel = new ImageFileModel();
       imageFileModel.setId(Long.valueOf(id));

       model.addAttribute("imageFileModel",imageFileModel);

        return "motorcycle/imageuploadform";
    }


    @GetMapping("motorcycle/{id}/motorcycleimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {

        Motorcycle motorcycle = motorcycleService.findById(Long.valueOf(id));
        if(motorcycle == null){
            throw new RuntimeException("there is no motorcycle with this id : " + id);
        }


        byte[] byteArray = new byte[motorcycle.getImage().length];
        int i= 0;

        for(Byte wrappedByte : motorcycle.getImage() ){

            byteArray[i++] = wrappedByte;
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is,response.getOutputStream());


    }


    @PostMapping("motorcycle/{id}/image")
    public String handleImagePost(@PathVariable String id, @ModelAttribute ImageFileModel fileModel, BindingResult bindingResult){

        new ImageValidator().validate(fileModel,bindingResult);

        if(bindingResult.hasErrors()){
            System.out.println("DEBUG INFO ::::::::::::::: inside error condition for this id : " + id);
            return "motorcycle/imageuploadform";
        }

        imageService.saveImageFile(Long.valueOf(id),fileModel.getFile());

        return "redirect:/motorcycle/" + id + "/show/";

    }


}
