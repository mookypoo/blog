package com.mooky.blog.domain.user.constraints;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import com.mooky.blog.domain.user.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 2자 ~ 15자 사이
 * <p>한글, 영어, 숫자 가능 
 */
@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameConstraints, String>{

  private final UserRepository userRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    String errorMessage = "";

    if (value == null || value.isEmpty()) {
      errorMessage = "Your usename has to be at least 2 characters";
    } else {
      byte[] valueInBytes = value.getBytes(StandardCharsets.ISO_8859_1);
      if (valueInBytes.length < 2 || valueInBytes.length > 15) {
        errorMessage = "Your usename has to be at between 2 and 15 characters";
      } else if (this.hasCharBesidesAlphabetNumberKorean(value)) {
        errorMessage = "영문, 한글, 숫자만 입력 가능합니다";
      }
    }

    if (userRepository.existsByUsername(value)) {
      errorMessage = "이미 사용중인 이름입니다";
    }

    if (!errorMessage.isEmpty()) {
      // context is null when unit testing
      if (context != null) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
      }
      return false;
    }

    return true;
  }

  /**
   * @return true if has non-alphabet, non-Korean, non-number char
   */
  private boolean hasCharBesidesAlphabetNumberKorean(String value) {
    //u3131 (ㄱ) - u314E (ㅎ) | u314F (ㅏ) - u3263 (ㅣ)
    final Pattern regex = Pattern.compile("[^A-Za-z0-9|\\u3131-\\u314E|\\u314F-\\u3163|\\uac00-\\ud7af]");
    return regex.matcher(value).find();
  }
  
}
