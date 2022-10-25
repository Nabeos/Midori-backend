package com.midorimart.managementsystem.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.exception.custom.CustomNotFoundException;
import com.midorimart.managementsystem.model.CustomError;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, CustomError> badRequestException(CustomBadRequestException ex) {
        return ex.getErrors();
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, CustomError> notFoundException(CustomNotFoundException ex) {
        return ex.getErrors();
    }

}
