package com.sam.springbootimagecrudexample.domain;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileModel {

    private Long id;
    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
