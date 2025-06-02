package com.mooky.pet_diary.domain.pet.dto;

import java.time.LocalDate;

import com.mooky.pet_diary.domain.pet.Pet;
import com.mooky.pet_diary.global.util.S3UrlUtil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
@ToString
public class PetDto {
    private final Long petId;

    @NotBlank(message = "pet name is required")
    @Size(min = 1, max = 50, message = "must provide the pet's name")
    private final String name;
    private final String species;
    private final String breed;
    private final LocalDate birthDate;
    private final LocalDate adoptionDate;
    private final String description;
    private final String profilePhoto;

    public static PetDto fromEntity(Pet pet) {
        return PetDto.builder()
                .petId(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .birthDate(pet.getBirthDate())
                .adoptionDate(pet.getAdoptionDate())
                .description(pet.getDescription())
                .profilePhoto(pet.getProfilePhoto())
                .build();
    }

    public String getProfilePhoto() {
        return S3UrlUtil.buildUrl(this.profilePhoto);
    }

}
