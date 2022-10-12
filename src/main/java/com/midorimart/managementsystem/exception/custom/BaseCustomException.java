package com.midorimart.managementsystem.exception.custom;

import java.util.HashMap;
import java.util.Map;

import com.midorimart.managementsystem.model.CustomError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BaseCustomException extends Exception {
    private Map<String, CustomError> errors;

    public BaseCustomException(CustomError customError){
        this.errors=new HashMap<>();
        this.errors.put("errors",customError);
    }

}
