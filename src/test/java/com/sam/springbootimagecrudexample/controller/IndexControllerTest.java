package com.sam.springbootimagecrudexample.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.service.MotorcycleService;

public class IndexControllerTest {

    @Mock
    MotorcycleService motorcycleService;

    @Mock
    Model model;

    IndexController  controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(motorcycleService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    @Test
    public void getIndexPage() throws Exception {
        //given
        Set<Motorcycle> motorcycles = new HashSet<>();
        motorcycles.add(new Motorcycle());

        Motorcycle recipe = new Motorcycle();
        recipe.setId(1L);
        motorcycles.add(recipe);

        when(motorcycleService.getMotorcycles()).thenReturn(motorcycles);

        ArgumentCaptor<Set<Motorcycle>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //When
        String viewName = controller.getIndexPage(model);

        //Then
        assertEquals("index",viewName);
        verify(motorcycleService,times(1)).getMotorcycles();
        verify(model,times(1)).addAttribute(eq("motorcycles"),argumentCaptor.capture());
        Set<Motorcycle> setInController = argumentCaptor.getValue();
        assertEquals(2,setInController.size());
    }


}