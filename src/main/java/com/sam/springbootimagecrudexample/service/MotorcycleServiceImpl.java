package com.sam.springbootimagecrudexample.service;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.exception.NotFoundException;
import com.sam.springbootimagecrudexample.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MotorcycleServiceImpl implements  MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;

    @Autowired
    public MotorcycleServiceImpl(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    @Override
    public Set<Motorcycle> getMotorcycles() {
        Set<Motorcycle> motorcycles = new HashSet<>();
        motorcycleRepository.findAll().iterator().forEachRemaining(motorcycles::add);
        return motorcycles;
    }

    @Override
    public Motorcycle findById(Long id) {
        Optional<Motorcycle> motorcycle = motorcycleRepository.findById(id);
        if(!motorcycle.isPresent()){
            throw new NotFoundException("Motorcycle not found with this id " + id );
        }
        return motorcycle.get();
    }


    @Override
    public Motorcycle save(Motorcycle motorcycle) {

        Motorcycle savedMotorcycle = motorcycleRepository.save(motorcycle);

        return savedMotorcycle;
    }

    @Override
    public void deleteById(Long id) {
        if(!motorcycleRepository.existsById(id)){
            throw new NotFoundException("There isn't already Motorcycle with this id : " + id);
        }
        motorcycleRepository.deleteById(id);
    }
}
