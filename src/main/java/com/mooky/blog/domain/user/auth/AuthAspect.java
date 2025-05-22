package com.mooky.blog.domain.user.auth;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.mooky.blog.domain.user.UserDto;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.global.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthAspect {

    private final UserRepository userRepository;

    @Pointcut("execution(* com.mooky.blog.domain.user.auth.AuthController.signUpViaEmail(..))")
    public void signUpViaEmail() {}
    
    @AfterReturning(value = "signUpViaEmail()", returning = "res")
    public void logSignUp(ApiResponse res) {
        log.info("[회원가입] {}", res.toString());
    }

    // Aspect로 바꾼 이유
    // (1) 비즈니스 로직이 아님 
    // (2) setter로 하던 repo로 update 하던 ApiResponse가 return되기 전에 실행됨 
    //   - 여기서 하면 ApiResponse 보낸 이후로여서 영향이 없음 
    //    - (사용자에게 영향이 없을려면 서비스보다는 controller에서 하는게 value return한 후여서 괜찮다고 판단 )
    @AfterReturning(pointcut = "execution(* com.mooky.blog.domain.user.auth.AuthController.emailLogin(..))", returning = "res")
    public void logLoginAndModifyLoginTime(ApiResponse res) {
        log.info("[로그인] {}, method=email", res.toString());
        this.userRepository.updateRecentLoginById(((UserDto) res.getPayload()).getUserId());
    }


}
