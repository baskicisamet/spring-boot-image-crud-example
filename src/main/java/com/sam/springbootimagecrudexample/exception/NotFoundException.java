package com.sam.springbootimagecrudexample.exception;

public class NotFoundException extends RuntimeException {

    public  NotFoundException(String message){
        super(message);
    }

}
