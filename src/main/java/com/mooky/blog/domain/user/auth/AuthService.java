package com.mooky.blog.domain.user.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mooky.blog.domain.user.User;
import com.mooky.blog.domain.user.UserDto;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.domain.user.User.SignUpType;
import com.mooky.blog.domain.user.auth.dto.LoginReq;
import com.mooky.blog.domain.user.auth.dto.UserSignUpReq;
import com.mooky.blog.global.config.AppConfig;
import com.mooky.blog.global.exception.ApiException.AuthException;
import com.mooky.blog.global.exception.ApiException.InUseException;
import com.mooky.blog.global.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AppConfig config;

    /**
     * check if email has already been used to sign up
     * @return true if available (= not in use)
     */
    public boolean isEmailAvailable(String email) {
        return this.userRepository.existsByEmail(email);
    }

    /**
     * check if username is in use
     * @return true if available (= not in use)
     */
    public boolean isUsernameAvailable(String username) {
        return !this.userRepository.existsByUsername(username);
    }

    // TODO email verification
    public UserDto signUpBlogUser(UserSignUpReq req, SignUpType signUpType) {
        if (this.userRepository.existsByEmail(req.getEmail())) {
            throw new InUseException("email_in_use", "이미 사용중인 이메일입니다", req.getEmail(), null);
        }

        if (this.userRepository.existsByUsername(req.getUsername())) {
            throw new InUseException("username_in_use", "이미 사용중인 이름입니다", req.getUsername(), null);
        }

        User userReq = new User.Builder().agreedMarketingTerms(req.isAgreeToMarketing())
                .email(req.getEmail())
                .username(req.getUsername())
                .password(this.encryptPW(req.getPassword()))
                .signupType(signUpType)
                .build();

        User savedUser = this.userRepository.save(userReq);
        return new UserDto(savedUser);
    }
    
    public UserDto emailLogin(LoginReq req) {
        User user = this.userRepository.findByEmail(req.getEmail())
            .orElseThrow(() -> {
                    throw new AuthException("incorrect_email", "이메일을 찾을 수 없습니다", req.getEmail(), "로그인 실패");
                });
        boolean hasCorrectPw = this.doesEncryptedMatchValue(req.getPassword(), user.getPassword());
        if (!hasCorrectPw) {
            throw new AuthException("incorrect_pw", "잘못된 비밀번호입니다", req.getEmail(), "로그인 실패");
        }
        String accessToken = this.jwtService.generateToken(user);
        return new UserDto(user, accessToken);
    }

    private String encryptPW(String pw) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(this.config.getBCryptStrength());
        String result = encoder.encode(pw);
        assert (encoder.matches(pw, result));
        return result;
    }

    private boolean doesEncryptedMatchValue(String value, String encrypted) {
        return new BCryptPasswordEncoder(this.config.getBCryptStrength()).matches(value, encrypted);
    }

}
