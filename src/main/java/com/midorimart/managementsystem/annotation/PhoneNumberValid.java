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
@Constraint(validatedBy = OrderValidator.class)
@Target({ ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR })
public @interface PhoneNumberValid {
  String message() default "Kiểm tra lại số điện thoại";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
