package com.mooky.blog.domain.user.signup;

import java.util.Set;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.user.constraints.groups.UserEmail;
import com.mooky.blog.domain.user.constraints.groups.UserSignUpInfo;
import com.mooky.blog.global.ApiResponse;
import com.mooky.blog.global.exception.ApiException.InvalidBodyException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("${mooky.endpoint}/v1/sign-up")
public class SignUpController {
  // TODO have as interface?
  private <T>void validation(T req, Class<?> groups) {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<T>> vios = validator.validate(req, groups);
    if (vios.size() != 0) {
      ConstraintViolation<T> vio = vios.iterator().next();
      throw new InvalidBodyException("invalid_body", vio.getMessage(), vio.getInvalidValue().toString());
    }
  }

  @PostMapping
  public ApiResponse signUpViaEmail(@Valid @RequestBody BlogUserSignUpReq req) {
    this.validation(req, UserSignUpInfo.class);
    return ApiResponse.ok("validated");
  }

  @Validated
  @PostMapping("/check/email")
  public ApiResponse checkEmail(@Valid @RequestBody BlogUserSignUpReq req) {
    this.validation(req, UserEmail.class);
    return ApiResponse.ok("validated");
  }

}
