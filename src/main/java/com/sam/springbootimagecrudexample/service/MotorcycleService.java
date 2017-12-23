package com.sam.springbootimagecrudexample.service;

import com.sam.springbootimagecrudexample.domain.Motorcycle;

import java.util.Set;

public interface MotorcycleService {

    Set<Motorcycle> getMotorcycles();
    Motorcycle findById(Long id);
    Motorcycle save(Motorcycle motorcycle);
    void deleteById(Long id);
}
