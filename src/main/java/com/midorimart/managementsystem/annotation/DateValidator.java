package com.midorimart.managementsystem.annotation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValid, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
    dfm.setLenient(false);
    try {
      dfm.parse(value);
    } catch (ParseException e) {
      return false;
    }

    return value!=null && !value.isBlank();
  }

}
