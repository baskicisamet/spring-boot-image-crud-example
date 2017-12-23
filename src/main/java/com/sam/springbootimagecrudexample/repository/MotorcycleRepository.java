package com.sam.springbootimagecrudexample.repository;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import org.springframework.data.repository.CrudRepository;

public interface MotorcycleRepository extends CrudRepository<Motorcycle,Long> {

    boolean existsById(Long id);
}
