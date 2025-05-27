package com.mooky.pet_diary.domain.pet;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.pet_diary.global.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

// TODO always need access token from here
@RestController
@RequestMapping("${mooky.endpoint}/v1/pets")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PetController {

    private final PetService petService;
    
    @PostMapping("/register")
    public ApiResponse registerPet() {
        return ApiResponse.ok("registered");
    }
}
