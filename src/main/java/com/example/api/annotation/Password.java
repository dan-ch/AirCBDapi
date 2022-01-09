package com.example.api.annotation;

import com.example.api.validation.PasswordConstrainValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordConstrainValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Password {
  String message() default "Hasło musi zawierać od 8 do 30 znaków w tym: małą i wielką literę, cyfrę i znak " +
      "specjalny";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
