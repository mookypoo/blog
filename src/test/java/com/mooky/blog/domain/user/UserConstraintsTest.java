package com.mooky.blog.domain.user;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mooky.blog.domain.user.constraints.EmailValidator;
import com.mooky.blog.domain.user.constraints.PasswordValidator;
import com.mooky.blog.domain.user.constraints.UsernameValidator;


public class UserConstraintsTest {
  
  /**
   * 2자 ~ 20자 사이
   * <p>
   * 한글, 영어, 숫자 가능
   */
    @Test
    public void usernameValidatorTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UsernameValidator validator = new UsernameValidator(userRepository);

        boolean isValidUsername = validator.isValid("!", null);
        assertFalse(isValidUsername);

        isValidUsername = validator.isValid("thisusernameismorethan15", null);
        assertFalse(isValidUsername);

        isValidUsername = validator.isValid("cannotUse!!", null);
        assertFalse(isValidUsername);

        isValidUsername = validator.isValid("한국어되요", null);
        assertTrue(isValidUsername);

        isValidUsername = validator.isValid("combination123", null);
        assertTrue(isValidUsername);

        Mockito.when(userRepository.existsByUsername("mooky")).thenReturn(true);
        isValidUsername = validator.isValid("mooky", null);
        assertFalse(isValidUsername);
    }
    
    /**
     * 8 ~ 20자
     * <p>영문, 숫자 및 특수문자 (?, !,@, #, $, %, -, _) 만 가능
     * <p>영문, 숫자 및 특수문자 (?, !,@, #, $, %, -, _) 중 2개 이상
     */
    @Test
    public void passwordValidatorTest() {
        PasswordValidator validator = new PasswordValidator();

        boolean isValidPassword = validator.isValid("!", null);
        assertFalse(isValidPassword);

        isValidPassword = validator.isValid("this!!!isaverylongpassword!!!!!", null);
        assertFalse(isValidPassword);

        isValidPassword = validator.isValid("onlyEnglish", null);
        assertFalse(isValidPassword);

        isValidPassword = validator.isValid("한국어는안되", null);
        assertFalse(isValidPassword);

        isValidPassword = validator.isValid("1234567890", null);
        assertFalse(isValidPassword);

        isValidPassword = validator.isValid("good!combination", null);
        assertTrue(isValidPassword);
    }

    @Test
    public void emailValidatorTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        EmailValidator emailValidator = new EmailValidator(userRepository);

        boolean isValidEmail = emailValidator.isValid("sookim", null);
        assertFalse(isValidEmail);

        isValidEmail = emailValidator.isValid("sookim@", null);
        assertFalse(isValidEmail);

        isValidEmail = emailValidator.isValid("sookim@g", null);
        assertFalse(isValidEmail);

        isValidEmail = emailValidator.isValid("sookim@gmail.c", null);
        assertFalse(isValidEmail);

        isValidEmail = emailValidator.isValid("sookim482.dev@gmail.com", null);
        assertTrue(isValidEmail);

        Mockito.when(userRepository.existsByEmail("sookim482.dev@gmail.com")).thenReturn(false);
        isValidEmail = emailValidator.isValid("sookim482.dev@gmail.com", null);
        assertTrue(isValidEmail);
    }
}
