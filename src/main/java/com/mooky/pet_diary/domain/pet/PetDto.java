package com.mooky.pet_diary.domain.pet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
@ToString
public class PetDto {
    private final Long petId;
    private final String name;
    private final String species;
    private final String breed;
    private final String birthDate; // "YYYY-MM-DD"
    private final String adoptionDate;
    private final String description;
    private final String profilePhoto; // TODO
}
