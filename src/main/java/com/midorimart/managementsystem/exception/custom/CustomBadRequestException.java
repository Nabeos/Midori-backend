package com.midorimart.managementsystem.exception.custom;

import com.midorimart.managementsystem.model.CustomError;

public class CustomBadRequestException extends BaseCustomException {

    public CustomBadRequestException(CustomError customError) {
        super(customError);
        
    }
    
}
