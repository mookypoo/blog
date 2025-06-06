package com.mooky.pet_diary.domain.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.pet_diary.domain.user.dto.UserProfileDto;
import com.mooky.pet_diary.global.ApiResponse;
import com.mooky.pet_diary.global.security.CurrentUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${mooky.endpoint}/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/me")
    public ApiResponse getUserProfile(@CurrentUser Long userId) {
        //UserDto user = this.userService.getUserDetails(userId);
        UserProfileDto user = this.userService.getUserProfile(userId);
        return ApiResponse.ok(user);
    }
}
