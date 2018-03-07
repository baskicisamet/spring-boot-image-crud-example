package com.sam.springbootimagecrudexample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.exception.NotFoundException;
import com.sam.springbootimagecrudexample.repository.MotorcycleRepository;
import com.sam.springbootimagecrudexample.service.MotorcycleService;

public class MotorcycleControllerTest {
	

	MotorcycleController controller;
	
	@Mock
	MotorcycleRepository motorcycleRepository;
	
	@Mock
	MotorcycleService motorcycleService;
	
	MockMvc mockMvc;
	
	
	@Before
	public void setUp() throws Exception{
		  MockitoAnnotations.initMocks(this);
		  controller = new MotorcycleController(motorcycleService);
		  mockMvc = MockMvcBuilders.standaloneSetup(controller)
	                .setControllerAdvice(new ControllerExceptionHandler())
	                .build();
	}
	
	
	@Test
    public void showById() throws Exception {

	
		//GIVEN
	  	Motorcycle motorcycle = new Motorcycle();
	    motorcycle.setId(1L);
	
	
	    when(motorcycleService.findById(anyLong())).thenReturn(motorcycle);
	    
	    //WHEN
	    mockMvc.perform(get("/motorcycle/1/show/"))
	    //THEN
	            .andExpect(status().isOk())
	            .andExpect(view().name("motorcycle/show"))
	            .andExpect(model().attributeExists("motorcycle"));

    }
	
	
	@Test
    public void getNewMotorcycleForm() throws Exception {
		
	        mockMvc.perform(get("/motorcycle/new"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("motorcycle/motorcycleform"))
	                .andExpect(model().attributeExists("motorcycle"));
	}
	

	@Test
	public void getUpdateMotorcycleForm() throws Exception{
		Motorcycle motorcycle = new Motorcycle();
	    motorcycle.setId(2L);

	    when(motorcycleService.findById((anyLong()))).thenReturn(motorcycle);

	        mockMvc.perform(get("/motorcycle/1/update"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("motorcycle/motorcycleform"))
	                .andExpect(model().attributeExists("motorcycle"));
	}
	
	
	@Test
    public void postNewMotorcycleForm() throws Exception {

		//GIVEN
		 Motorcycle motorcycle = new Motorcycle();
		 motorcycle.setId(2L);
		 
        when(motorcycleService.save((any(Motorcycle.class)))).thenReturn(motorcycle);
        
        //WHEN
        mockMvc.perform(post("/motorcycle")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("model", "sometink")
                .param("power","133")
                .param("displacement", "500")
                .param("description", "somethink")
                .param("price", "500")
                .param("type", "somethink")
                .param("brand", "somethink")
        )
        //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/motorcycle/2/show"));

    }
	
	
	@Test
    public void deleteById() throws Exception {
        mockMvc.perform(get("/motorcycle/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(motorcycleService, times(1)).deleteById(anyLong());
    }
	
	@Test
	public void testMotorcycleNotFound() throws Exception {
		  when(motorcycleService.findById(anyLong())).thenThrow(NotFoundException.class);

	        mockMvc.perform(get("/motorcycle/1/show/"))
	                .andExpect(status().isNotFound())
	                .andExpect(view().name("errorpage"))
	                .andExpect(model().attributeExists("errorTitle"));
	}
	
	 @Test
    public void postNewMotorcycleFormValidationFail() throws Exception {
	 
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(2L);

        when(motorcycleService.save(any(Motorcycle.class))).thenReturn(motorcycle);

        mockMvc.perform(post("/motorcycle")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("displacement", "inValidateData")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("motorcycle"))
                .andExpect(view().name("motorcycle/motorcycleform"));
    }
	
	 
	

}
