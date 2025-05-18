package com.mooky.blog.domain.user.signup;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mooky.blog.domain.user.User;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.domain.user.User.SignUpType;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpService {
  
  private final UserRepository userRepository;
  private @Value("${mooky.bCryptStrength}") int bCryptStrength;

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
    // Optional<String> UsernameInUse = this.userRepository.existsByUsername(Username);
    // return !UsernameInUse.isPresent();
    return !this.userRepository.existsByUsername(username);
  }

  // TODO email verification
  // TODO sign up log in aspect??
  public void signUpBlogUser(UserSignUpReq req, SignUpType signUpType) {
    // TODO should I use Builder or constructor
    User userReq = new User.Builder().agreedMarketingTerms(req.isAgreeToMarketing())
        .email(req.getEmail())
        .username(req.getUsername())
        .password(this.encryptPW(req.getPassword()))
        .signupType(signUpType)
        .build();
    this.userRepository.save(userReq);
  }
  
  private String encryptPW(String pw) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(this.bCryptStrength);
    String result = encoder.encode(pw);
    assert (encoder.matches(pw, result));
    return result;
  }

}
