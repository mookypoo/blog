package com.mooky.blog.domain.user.constraints;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraints, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern regex = Pattern.compile("\\w+@\\w+\\.\\w{2,}");
        return regex.matcher(value).find();
    }
  
}
