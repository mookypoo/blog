package com.mooky.pet_diary.domain.health_entry.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PetHealthEntryForm {
    private final Long petId;
    private final String petName;
    private final Float weight;
    private final String weightUnit;
}
