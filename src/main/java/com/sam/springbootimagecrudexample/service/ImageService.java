package com.sam.springbootimagecrudexample.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(Long motorcycleId, MultipartFile file);
}
