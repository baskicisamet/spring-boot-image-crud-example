package com.sam.springbootimagecrudexample.controller;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.service.ImageService;
import com.sam.springbootimagecrudexample.service.MotorcycleService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

        model.addAttribute("motorcycle",motorcycleService.findById(Long.valueOf(id)));

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
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id),file);

        return "redirect:/motorcycle/" + id + "/show/";

    }


}
