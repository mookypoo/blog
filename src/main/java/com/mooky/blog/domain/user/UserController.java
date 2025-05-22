package com.mooky.blog.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.global.ApiResponse;

@RestController
@RequestMapping("${mooky.endpoint}/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/{userId}")
    public ApiResponse getUserDetails(@PathVariable("userId") int userId) {
        UserDto user = this.userService.getUserDetails(Integer.toUnsignedLong(userId));
        return ApiResponse.ok(user);
    }
}
