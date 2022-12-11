package com.midorimart.managementsystem.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR })
public @interface DateValid {
  String message() default "Sai format ngày giao hoặc thiếu ngày giao";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
