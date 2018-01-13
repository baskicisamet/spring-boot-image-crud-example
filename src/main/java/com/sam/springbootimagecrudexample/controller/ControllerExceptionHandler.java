package com.sam.springbootimagecrudexample.controller;

import com.sam.springbootimagecrudexample.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class ControllerExceptionHandler{


    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handlerNoHandlerFoundException() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorpage");
        modelAndView.addObject("errorTitle", "404 Not Found ");
        modelAndView.addObject("errorDescription", "The page you are looking for is not available now!!! ");
        System.out.println("ERROR :::::::::::::::::::::: no handler");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception exception) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorpage");
        modelAndView.addObject("errorTitle", "Unknown Exception ! ");
        modelAndView.addObject("errorDescription", "please contact your administration!\n\n" + exception.getMessage());

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception){


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorpage");
        modelAndView.addObject("errorTitle","400 BAD REQUEST");
        modelAndView.addObject("errorDescription",exception.getMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorpage");
        modelAndView.addObject("errorTitle","404 NOT FOUND");
        modelAndView.addObject("errorDescription",exception.getMessage());
        return modelAndView;
    }

}
