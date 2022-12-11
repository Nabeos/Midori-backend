package com.midorimart.managementsystem.exception;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> notEmptyException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        if (e.getConstraintViolations().iterator().hasNext()) {
            errors.put("error", e.getMessage());
        }
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> phoneNumberException(HttpMessageNotReadableException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Lỗi truyền dữ liệu");
        return errors;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> typeMismatchException(MethodArgumentTypeMismatchException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Lỗi truyền dữ liệu");
        return errors;
    }

}
