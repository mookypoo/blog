package com.mooky.blog.domain.user;

import org.springframework.stereotype.Service;

import com.mooky.blog.global.exception.ApiException.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetails getUserDetails(Long userId) {
        UserDetails user = this.userRepository.getUserDetails(userId).orElseThrow(
            () -> new NotFoundException("user_not_found", "no such user", userId.toString())
        );
        return user;
    }
}
