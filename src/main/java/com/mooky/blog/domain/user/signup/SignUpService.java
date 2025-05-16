package com.mooky.blog.domain.user.signup;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mooky.blog.domain.user.entity.UserEntity;
import com.mooky.blog.domain.user.entity.UserEntity.SignUpType;
import com.mooky.blog.domain.user.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpService {
  
  private final UserRepository userRepository;

  /**
   * check if email has already been used to sign up
   * @return true if available (= not in use)
   */
  public boolean isEmailAvailable(String email) {
    Optional<String> emailInUse = this.userRepository.unavailableEmail(email);
    return !emailInUse.isPresent();
  }

  /**
   * check if username is in use
   * @return true if available (= not in use)
   */
  public boolean isUsernameAvailable(String Username) {
    Optional<String> UsernameInUse = this.userRepository.unavailableUsername(Username);
    return !UsernameInUse.isPresent();
  }

  // TODO email verification
  public void signUpBlogUser(BlogUserSignUpReq req, SignUpType signUpType) {
    UserEntity userReq = new UserEntity.Builder().agreedMarketingTerms(req.isAgreeToMarketing())
      .email(req.getEmail())
      .username(req.getUsername())
      .password(req.getPassword())
      .signupType(signUpType)
      .build();
    this.userRepository.save(userReq);
  }

}
