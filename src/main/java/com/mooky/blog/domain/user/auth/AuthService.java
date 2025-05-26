package com.mooky.blog.domain.user.auth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.mooky.blog.domain.user.User;
import com.mooky.blog.domain.user.UserDto;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.domain.user.User.SignUpType;
import com.mooky.blog.domain.user.auth.dto.EmailLoginReq;
import com.mooky.blog.domain.user.auth.dto.GoogleLoginReq;
import com.mooky.blog.domain.user.auth.dto.UserSignUpReq;
import com.mooky.blog.global.config.AppConfig;
import com.mooky.blog.global.exception.ApiException.AuthException;
import com.mooky.blog.global.exception.ApiException.InUseException;
import com.mooky.blog.global.security.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final GoogleTokenService googleTokenService;
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
    

    public UserDto signUpByEmail(UserSignUpReq req) {
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
                .signupType(SignUpType.EMAIL)
                .build();

        User savedUser = this.userRepository.save(userReq);
        String accessToken = this.jwtService.generateToken(savedUser);
        return new UserDto(savedUser, accessToken);
    }

    public UserDto signUpByGoogle(UserSignUpReq req) {
        // google already checks existsByEmail before sign up page
        if (this.userRepository.existsByUsername(req.getUsername())) {
            throw new InUseException("username_in_use", "이미 사용중인 이름입니다", req.getUsername(), null);
        }

        Payload payload = this.googleTokenService.verifyGoogleIdToken(req.getGoogleIdToken());

        User userReq = new User.Builder().agreedMarketingTerms(req.isAgreeToMarketing())
                .email(payload.getEmail())
                .username(req.getUsername())
                .ssoId(payload.getSubject())
                .signupType(SignUpType.GOOGLE)
                .build();

        User savedUser = this.userRepository.save(userReq);
        String accessToken = this.jwtService.generateToken(savedUser);
        return new UserDto(savedUser, accessToken);
    }

    
    public UserDto emailLogin(EmailLoginReq req) {
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

    /**
     * returns null if the user has not signed up yet --> requires sign up process
     */
    public UserDto googleLogin(GoogleLoginReq req) {
        // verify user exists
        Optional<User> userOpt = this.userRepository.findByEmail(req.getEmail());
        // new user - sav
        if (!userOpt.isPresent()) {
            return null;
        } else {
            String accessToken = this.jwtService.generateToken(userOpt.get());
            return new UserDto(userOpt.get(), accessToken);
        }
    }

}
