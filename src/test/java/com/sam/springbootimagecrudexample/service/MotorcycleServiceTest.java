package com.sam.springbootimagecrudexample.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.repository.MotorcycleRepository;

public class MotorcycleServiceTest {

	MotorcycleService motorcycleService;

	@Mock
	MotorcycleRepository motorcycleRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		motorcycleService = new MotorcycleServiceImpl(motorcycleRepository);
	}

	@Test
	public void getMotorcycles() throws Exception {

		// GIVEN
		Motorcycle motorcycle = new Motorcycle();
		Motorcycle motorcycle2 = new Motorcycle();
		Set<Motorcycle> motorcycles = new HashSet();
		motorcycles.add(motorcycle);
		motorcycles.add(motorcycle2);

		when(motorcycleRepository.findAll()).thenReturn(motorcycles);

		// WHEN
		Set<Motorcycle> resultMotorcycles = motorcycleService.getMotorcycles();

		// THEN
		assertEquals(resultMotorcycles.size(), 2);
		verify(motorcycleRepository, times(1)).findAll();

	}

	@Test
	public void findById() throws Exception {

		// GIVEN
		Motorcycle motorcycle = new Motorcycle();
		motorcycle.setId(1L);
		Optional<Motorcycle> motorcycleOptional = Optional.of(motorcycle);

		when(motorcycleRepository.findById(anyLong())).thenReturn(motorcycleOptional);
		// WHEN
		Motorcycle resultMotorcycle = motorcycleService.findById(1L);
		// THEN
		assertNotNull("Motorcycle cannot null", resultMotorcycle);
		verify(motorcycleRepository, times(1)).findById(anyLong());

	}

	@Test
	public void save() throws Exception {

		// GIVEN
		Motorcycle motorcycle = new Motorcycle();
		motorcycle.setId(1L);
		Optional<Motorcycle> motorcycleOptional = Optional.of(motorcycle);

		when(motorcycleRepository.findById(anyLong())).thenReturn(motorcycleOptional);
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);

		// WHEN
		Motorcycle resultMotorcycle = motorcycleService.save(motorcycle);

		// THEN
		assertNotNull("Motorcycle cannot null", resultMotorcycle);
		verify(motorcycleRepository, times(1)).save(any());

	}

	@Test
	public void deleteById() throws Exception {

		// GIVEN
		Long id = 1L;
		when(motorcycleRepository.existsById(id)).thenReturn(true);

		// WHEN
		motorcycleService.deleteById(id);

		// THEN
		verify(motorcycleRepository, times(1)).deleteById(anyLong());

	}

}
