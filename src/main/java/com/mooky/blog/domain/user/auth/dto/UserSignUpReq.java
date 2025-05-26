package com.mooky.blog.domain.user.auth.dto;

import com.mooky.blog.domain.user.constraints.EmailConstraints;
import com.mooky.blog.domain.user.constraints.PasswordConstraints;
import com.mooky.blog.domain.user.constraints.UsernameConstraints;
import com.mooky.blog.domain.user.constraints.groups.UserEmail;
import com.mooky.blog.domain.user.constraints.groups.UserSignUpInfo;
import com.mooky.blog.domain.user.constraints.groups.GoogleSignUpInfo;
import com.mooky.blog.domain.user.constraints.groups.Password;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@GroupSequence({ UserSignUpInfo.class, Password.class, UserEmail.class, UserSignUpReq.class})
public class UserSignUpReq {

    @EmailConstraints(groups = { UserEmail.class })
    private final String email;

    @UsernameConstraints(groups = { UserSignUpInfo.class })
    private final String username;

    // not required for google sign up
    @PasswordConstraints(groups = { Password.class })
    private String password;

    @NotBlank(message = "google id token을 입력하세요", groups = { GoogleSignUpInfo.class })
    @NotNull(message = "google id token을 입력하세요", groups = { GoogleSignUpInfo.class })
    private String googleIdToken;

    @AssertTrue(message = "필수 약관에 동의하셔야 회원으로 가입할 수 있습니다", groups = UserSignUpInfo.class)
    private final boolean agreeToTerms;
    
    private final boolean agreeToMarketing;

    public UserSignUpReq(String email, String username, String password, boolean agreeToTerms, boolean agreeToMarketing) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.agreeToTerms = agreeToTerms;
        this.agreeToMarketing = agreeToMarketing;
    }
}
