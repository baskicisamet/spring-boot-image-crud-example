package com.sam.springbootimagecrudexample.service;

import com.sam.springbootimagecrudexample.domain.Motorcycle;
import com.sam.springbootimagecrudexample.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements  ImageService {

    private final MotorcycleRepository motorcycleRepository;

    public ImageServiceImpl(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long motorcycleId, MultipartFile file) {

        try{
            Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for(byte b : file.getBytes()){
                byteObjects[i++] =  b;
            }

            motorcycle.setImage(byteObjects);


            motorcycleRepository.save(motorcycle);
        }catch (IOException e){

            e.printStackTrace();
        }



    }
}
