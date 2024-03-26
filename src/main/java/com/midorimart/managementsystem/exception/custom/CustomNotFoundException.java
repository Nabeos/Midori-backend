package com.midorimart.managementsystem.exception.custom;

import com.midorimart.managementsystem.model.CustomError;

public class CustomNotFoundException extends BaseCustomException{

    public CustomNotFoundException(CustomError customError) {
        super(customError);
    }

}
