package com.sam.springbootimagecrudexample.repository;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.springbootimagecrudexample.domain.Motorcycle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MotorcycleRepositroryIT {
	
	 @Autowired
	 MotorcycleRepository motorcycleRepository;

    @Test
    public void findById() throws Exception {

        Optional<Motorcycle> motorcycleOptional = motorcycleRepository.findById(1L);

        assertEquals(Long.valueOf(1L),motorcycleOptional.get().getId());
    }


}
