package com.mooky.blog.domain.user.auth;

import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.user.User.SignUpType;
import com.mooky.blog.domain.user.constraints.groups.UserEmail;
import com.mooky.blog.domain.user.constraints.groups.UserSignUpInfo;
import com.mooky.blog.domain.user.constraints.groups.Username;
import com.mooky.blog.global.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("${mooky.endpoint}/v1/auth")
public class AuthController {

    private final AuthService signUpService;

    @PostMapping("/signup/email")
    public ApiResponse signUpViaEmail(@Validated({UserSignUpInfo.class, UserEmail.class, Username.class}) @RequestBody UserSignUpReq req) {
        this.signUpService.signUpBlogUser(req, SignUpType.EMAIL);
        return ApiResponse.ok("회원가입 성공");
    }

    @PostMapping("/check/email")
    public ApiResponse checkEmail(@Validated(UserEmail.class) @RequestBody UserSignUpReq req) {
        return ApiResponse.ok(Map.of(
            "isEmailAvailable", this.signUpService.isEmailAvailable(req.getEmail()),
            "email", req.getEmail()));
    }
    
    @PostMapping("/check/username")
    public ApiResponse checkUsername(@Validated(Username.class) @RequestBody UserSignUpReq req) {
        return ApiResponse.ok(Map.of(
            "isUsernameAvailable", this.signUpService.isUsernameAvailable(req.getUsername()),
            "username", req.getUsername()));
    }

}
