package com.sam.springbootimagecrudexample.service;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.repository.MotorcycleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MotorcycleServiceIT {

	@Autowired
	MotorcycleService motorcycleService;
	
	@Autowired
	MotorcycleRepository motorcycleRepository;
	
	@Transactional
	@Test
	public void updateDescription() throws Exception {
		
		//given
        Motorcycle motorcycleToUpdate = motorcycleRepository.findById(1L).get();
       

        //when
        motorcycleToUpdate.setDescription("New description");
        Motorcycle savedMotorcycle = motorcycleService.save(motorcycleToUpdate);

        //then
        assertEquals("New description", savedMotorcycle.getDescription());
        assertEquals(motorcycleToUpdate.getId(), savedMotorcycle.getId());
        assertEquals(motorcycleToUpdate.getPower(),savedMotorcycle.getPower());
        assertEquals(motorcycleToUpdate.getModel(), savedMotorcycle.getModel());
        
	}
	
}
