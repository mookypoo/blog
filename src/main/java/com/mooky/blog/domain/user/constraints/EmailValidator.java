package com.mooky.blog.domain.user.constraints;

import java.util.regex.Pattern;

import com.mooky.blog.domain.user.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailConstraints, String>{

  private final UserRepository userRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Pattern regex = Pattern.compile("\\w+@\\w+\\.\\w{2,}");
    boolean isValid = regex.matcher(value).find();

    if (isValid) {
      isValid = !this.userRepository.existsByEmail(value);
    }

    return isValid;
  }
  
}
