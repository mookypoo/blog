package com.mooky.blog.domain.user.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mooky.blog.domain.user.User;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.domain.user.User.SignUpType;
import com.mooky.blog.global.config.SecurityConfig;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfig config;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testShouldSaveUser_Email() {
        when(this.config.getBCryptStrength()).thenReturn(4);
       
        UserSignUpReq req = new UserSignUpReq("sookim482.dev@gmail.com", "mooky", "password", true, false);
        // Create a matcher that compares only relevant fields
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        // Mock the save method to return the same user that was passed in
        when(this.userRepository.save(userCaptor.capture())).thenAnswer(i -> i.getArgument(0));

        User savedUser = this.authService.signUpBlogUser(req, SignUpType.EMAIL);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("sookim482.dev@gmail.com");
        assertThat(savedUser.getPassword().startsWith("$2a$"));  
    }


    @Test
    public void testShouldNotSaveUser() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // other violations are unit tested within UserConstraintsTest
        UserSignUpReq req = new UserSignUpReq("sookim482.dev@gmail.com", "mooky", "passwo!!", false, false);
        Set<ConstraintViolation<UserSignUpReq>> violations = validator.validate(req);
        assertThat(violations.size()).isEqualTo(1);

    }
}
