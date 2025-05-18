package com.mooky.blog.domain.user.signup;

import com.mooky.blog.domain.user.constraints.EmailConstraints;
import com.mooky.blog.domain.user.constraints.PasswordConstraints;
import com.mooky.blog.domain.user.constraints.UsernameConstraints;
import com.mooky.blog.domain.user.constraints.groups.UserEmail;
import com.mooky.blog.domain.user.constraints.groups.UserSignUpInfo;
import com.mooky.blog.domain.user.constraints.groups.Username;

import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(force = true)
@ToString
public class UserSignUpReq {

  @EmailConstraints(groups = { UserEmail.class, UserSignUpInfo.class })
  private final String email;

  @UsernameConstraints(groups = { Username.class, UserSignUpInfo.class })
  private final String username;

  @PasswordConstraints(groups = { UserSignUpInfo.class })
  private final String password;

  @AssertTrue(message = "필수 약관에 동의하셔야 회원으로 가입할 수 있습니다", groups = UserSignUpInfo.class)
  private final boolean agreeToTerms;
  
  private final boolean agreeToMarketing;

  // Assert.isTrue()
}
