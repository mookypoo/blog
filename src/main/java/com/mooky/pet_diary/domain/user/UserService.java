package com.mooky.pet_diary.domain.user;

import org.springframework.stereotype.Service;

import com.mooky.pet_diary.global.exception.ApiException.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserDetails(Long userId) {
        UserDto user = this.userRepository.getUserDetails(userId).orElseThrow(
            () -> new NotFoundException("user_not_found", "no such user", userId.toString(), null)
        );
        return user;
    }
}
