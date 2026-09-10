package com.backend.envsysbackend.web.aop;

import com.backend.envsysbackend.web.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e){
        return new R (5001,e.getMessage(),null);
    }
}
