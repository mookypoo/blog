package com.mooky.pet_diary.domain.pet;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.pet_diary.global.ApiResponse;
import com.mooky.pet_diary.global.security.CurrentUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO always need access token from here
@Slf4j
@RestController
@RequestMapping("${mooky.endpoint}/v1/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    
    @PostMapping("/register")
    public ApiResponse registerPet(@RequestBody PetDto req, @CurrentUser Long currentUser) {
        log.debug("current user: {}", currentUser);
        return ApiResponse.ok("registered");
    }
}
