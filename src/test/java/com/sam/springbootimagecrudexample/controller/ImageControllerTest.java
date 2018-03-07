package com.sam.springbootimagecrudexample.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sam.springbootimagecrudexample.domain.ImageFileModel;
import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.service.ImageService;
import com.sam.springbootimagecrudexample.service.MotorcycleService;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    MotorcycleService motorcycleService;

    ImageController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ImageController(imageService,motorcycleService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    
    @Test
    public void showUploadForm() throws Exception{

        //given
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(1L);

        when(motorcycleService.findById(anyLong())).thenReturn(motorcycle);

        //when
        mockMvc.perform(get("/motorcycle/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("imageFileModel"));


    }
    
    @Test
    public void renderImageFromDB() throws Exception {

        //given
        Motorcycle motorcycle= new Motorcycle();
        motorcycle.setId(1L);

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }

        motorcycle.setImage(bytesBoxed);

        when(motorcycleService.findById(anyLong())).thenReturn(motorcycle);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/motorcycle/1/motorcycleimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] reponseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, reponseBytes.length);
    }

   
    @Ignore
    @Test
    public void handleImagePost() throws Exception {
    	//GIVEN
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "image.jpeg", "text/plain",
                        "image byte data..".getBytes());
        ImageFileModel imageFileModel = new ImageFileModel();
        imageFileModel.setId(5L);
        imageFileModel.setFile(multipartFile);
        
        //WHEN & THEN
        mockMvc.perform(multipart("/motorcycle/1/image")
        		.flashAttr("fileModel", imageFileModel))
        		
//        		(post("/motorcycle/1/image").contentType(MediaType.MULTIPART_FORM_DATA)
//        		.param("id", "1"))
//        		.param("file", multipartFile)
        
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/motorcycle/1/show/"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }



   

    
    @Test
    public void testGetImageNumberFormatException() throws Exception {

        mockMvc.perform(get("/motorcycle/asdf/motorcycleimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("errorpage"));
    }


}
