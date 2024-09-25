package com.lct.bus.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExeptionController {
    @ExceptionHandler(Exception.class)
    public String exeption(Exception ex){
        ex.printStackTrace();
        return "exeption";
    }
}
