package com.mooky.blog.domain.user.auth;

import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.user.UserDto;
import com.mooky.blog.domain.user.User.SignUpType;
import com.mooky.blog.domain.user.auth.dto.LoginReq;
import com.mooky.blog.domain.user.auth.dto.UserSignUpReq;
import com.mooky.blog.domain.user.constraints.groups.UserEmail;
import com.mooky.blog.domain.user.constraints.groups.UserSignUpInfo;
import com.mooky.blog.domain.user.constraints.groups.Username;
import com.mooky.blog.global.ApiResponse;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("${mooky.endpoint}/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup/email")
    public ApiResponse signUpViaEmail(@Validated({UserSignUpInfo.class, UserEmail.class, Username.class}) @RequestBody UserSignUpReq req) {
        UserDto user = this.authService.signUpBlogUser(req, SignUpType.EMAIL);
        return ApiResponse.ok(user);
    }

    @PostMapping("/check/email")
    public ApiResponse checkEmail(@Validated(UserEmail.class) @RequestBody UserSignUpReq req) {
        return ApiResponse.ok(Map.of(
            "isEmailAvailable", this.authService.isEmailAvailable(req.getEmail()),
            "email", req.getEmail()));
    }
    
    @PostMapping("/check/username")
    public ApiResponse checkUsername(@Validated(Username.class) @RequestBody UserSignUpReq req) {
        return ApiResponse.ok(Map.of(
            "isUsernameAvailable", this.authService.isUsernameAvailable(req.getUsername()),
            "username", req.getUsername()));
    }
    
    @PostMapping("/login/email") 
    public ApiResponse emailLogin(@Valid @RequestBody LoginReq req) {
        UserDto user = this.authService.emailLogin(req);
        return ApiResponse.ok(user);
    }
 
}
