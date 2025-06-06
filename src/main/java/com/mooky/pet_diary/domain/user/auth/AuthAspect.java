package com.mooky.pet_diary.domain.user.auth;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.mooky.pet_diary.domain.user.dto.UserDto;
import com.mooky.pet_diary.domain.user.repository.UserRepository;
import com.mooky.pet_diary.global.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthAspect {

    private final UserRepository userRepository;

    @Pointcut("execution(* com.mooky.blog.domain.user.auth.AuthController.signUpByEmail(..))")
    public void signUpByEmail() {}
    
    @Pointcut("execution(* com.mooky.blog.domain.user.auth.AuthController.signUpByGoogle(..))")
    public void signUpByGoogle() {}
    
    @AfterReturning(value = "signUpByEmail() || signUpByGoogle()", returning = "res")
    public void logSignUp(ApiResponse res) {
        log.info("[회원가입] {}", res.toString());
    }

    @Pointcut("execution(* com.mooky.blog.domain.user.auth.AuthController.emailLogin(..))")
    public void emailLogin() {
    }

    @Pointcut("execution(* com.mooky.blog.domain.user.auth.AuthController.googleLogin(..))")
    public void googleLogin() {}

    // Aspect로 바꾼 이유
    // (1) 비즈니스 로직이 아님 
    // (2) setter로 하던 repo로 update 하던 ApiResponse가 return되기 전에 실행됨 
    //   - 여기서 하면 ApiResponse 보낸 이후로여서 영향이 없음 
    //    - (사용자에게 영향이 없을려면 서비스보다는 controller에서 하는게 value return한 후여서 괜찮다고 판단 )
    @AfterReturning(value = "emailLogin() || googleLogin()", returning = "res")
    public void logLoginAndModifyLoginTime(ApiResponse res) {
        if (res.getPayload() instanceof UserDto) {
            log.info("[로그인] {}, method=email", res.toString());
            this.userRepository.updateRecentLoginById(((UserDto) res.getPayload()).getUserId());
        } 
    }

}
