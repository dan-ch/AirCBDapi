package com.example.api.validation;

import com.example.api.annotation.Password;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstrainValidator implements ConstraintValidator<Password, String> {
  @Override
  public void initialize(Password constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    PasswordValidator validator = new PasswordValidator(Arrays.asList(
        new LengthRule(8, 30),
        new CharacterRule(PolishCharacterData.UpperCase, 1),
        new CharacterRule(PolishCharacterData.LowerCase, 1),
        new CharacterRule(EnglishCharacterData.Special, 1),
        new CharacterRule(EnglishCharacterData.Digit, 1),
        new WhitespaceRule()));
    RuleResult result = validator.validate(new PasswordData(password));
    return result.isValid();
  }
}
