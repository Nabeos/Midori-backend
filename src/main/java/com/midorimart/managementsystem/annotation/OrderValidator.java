package com.midorimart.managementsystem.annotation;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<PhoneNumberValid, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    // TODO Auto-generated method stub
    return value != null && value.matches(
        "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")
        && (value.length() > 9 && value.length() < 11);
  }
}
