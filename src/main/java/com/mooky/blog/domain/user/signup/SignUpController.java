package com.mooky.blog.domain.user.signup;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.user.constraints.groups.UserEmail;
import com.mooky.blog.domain.user.constraints.groups.UserSignUpInfo;
import com.mooky.blog.domain.user.constraints.groups.Username;
import com.mooky.blog.global.ApiResponse;
import com.mooky.blog.global.exception.ApiException.InvalidBodyException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("${mooky.endpoint}/v1/sign-up")
public class SignUpController {

  private final SignUpService signUpService;

  // TODO have as interface?
  /**
   * validates using Validator
   * 
   * @param req    (object to be validated)
   * @param groups validation groups
   * @see jakarta.validation.Validator.validate
   * @throws InvalidBodyException 
   */
  private <T> void validation(T req, Class<?> groups) {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<T>> vios = validator.validate(req, groups);
    if (vios.size() != 0) {
      ConstraintViolation<T> vio = vios.iterator().next();
      throw new InvalidBodyException("invalid_body", vio.getMessage(), vio.getInvalidValue().toString());
    }
  }

  @PostMapping("/email")
  public ApiResponse signUpViaEmail(@Valid @RequestBody BlogUserSignUpReq req) {
    this.validation(req, UserSignUpInfo.class);
    return ApiResponse.ok("validated");
  }

  @PostMapping("/check/email")
  public ApiResponse checkEmail(@Valid @RequestBody BlogUserSignUpReq req) {
    this.validation(req, UserEmail.class);
    return ApiResponse.ok(Map.of(
        "isEmailAvailable", this.signUpService.isEmailAvailable(req.getEmail()),
        "email", req.getEmail()));
  }
  
  @PostMapping("/check/username")
  public ApiResponse checkUsername(@Valid @RequestBody BlogUserSignUpReq req) {
    this.validation(req, Username.class);
    return ApiResponse.ok(Map.of(
        "isUsernameAvailable", this.signUpService.isUsernameAvailable(req.getUsername()),
        "username", req.getUsername()));
  }

}
