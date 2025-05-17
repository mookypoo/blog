package com.mooky.blog.domain.user.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.global.ApiResponse;

@RestController
@RequestMapping("${mooky.endpoint}/v1/auth")
public class AuthController {

  // @PostMapping("/login/email")
  // public ApiResponse emailLogin(@RequestBody LoginReq googleLoginReq) {
  //   return ApiResponse.ok(this.authService.googleLogin(googleLoginReq));
  // }
}
