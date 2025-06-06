package com.mooky.pet_diary.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserWithPetSummaryProjection {
    private final Long userId;
    private final String username;
    private final String email;
    private final Long petId;
    private final String petName;
    private final String petProfilePhoto;
}
