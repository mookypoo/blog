package com.mooky.pet_diary.domain.pet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PetSummaryDto {
    private final Long id;
    private final String name;
    private final String profilePhoto;
}
