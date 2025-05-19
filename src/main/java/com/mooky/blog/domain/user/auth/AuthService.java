package com.mooky.blog.domain.user.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mooky.blog.domain.user.User;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.domain.user.User.SignUpType;
import com.mooky.blog.global.config.SecurityConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  
    private final UserRepository userRepository;
    private final SecurityConfig config;

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
    // TODO sign up log in aspect??
    public User signUpBlogUser(UserSignUpReq req, SignUpType signUpType) {
        // TODO should I use Builder or constructor
        User userReq = new User.Builder().agreedMarketingTerms(req.isAgreeToMarketing())
            .email(req.getEmail())
            .username(req.getUsername())
            .password(this.encryptPW(req.getPassword()))
            .signupType(signUpType)
            .build();
        return this.userRepository.save(userReq);
    }
    
    private String encryptPW(String pw) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(this.config.getBCryptStrength());
        String result = encoder.encode(pw);
        assert (encoder.matches(pw, result));
        return result;
    }

}
