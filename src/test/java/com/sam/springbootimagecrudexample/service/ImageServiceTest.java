package com.sam.springbootimagecrudexample.service;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.repository.MotorcycleRepository;

public class ImageServiceTest {
	
	@Mock
	MotorcycleRepository motorcycleRepository;
	
	ImageService imageService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(motorcycleRepository);
	}
	
	 @Test
	    public void saveImageFile() throws Exception {

	        //given
	        Long id = 1L;
	        MultipartFile multipartFile = new MockMultipartFile("imagefile", "image.txt", "text/plain",
	                "representational image byte data..".getBytes());

	        Motorcycle motorcycle = new Motorcycle();
	        motorcycle.setId(id);
	        Optional<Motorcycle> motorcycleOptional = Optional.of(motorcycle);

	        when(motorcycleRepository.findById(anyLong())).thenReturn(motorcycleOptional);

	        ArgumentCaptor<Motorcycle> argumentCaptor = ArgumentCaptor.forClass(Motorcycle.class);

	        //when
	        imageService.saveImageFile(id, multipartFile);

	        
	        //then
	        verify(motorcycleRepository, times(1)).save(argumentCaptor.capture());
	        Motorcycle savedRecipe = argumentCaptor.getValue();
	        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	    }

}
