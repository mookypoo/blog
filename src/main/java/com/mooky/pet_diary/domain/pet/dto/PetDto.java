package com.mooky.pet_diary.domain.pet.dto;

import java.time.LocalDate;

import com.mooky.pet_diary.domain.pet.Pet;

import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 50, message = "must provide the pet's name")
    private final String name;
    private final String species;
    private final String breed;
    private final String birthDate; // "YYYY-MM-DD"
    private final String adoptionDate;
    private final String description;
    private final String profilePhoto; // TODO 

    public PetDto(Pet pet) {
        this.petId = pet.getId();
        this.name = pet.getName();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.birthDate = this.convertDate(pet.getBirthDate());
        this.adoptionDate = this.convertDate(pet.getAdoptionDate());
        this.description = pet.getDescription();
        this.profilePhoto = "https://pet-diary-bucket.s3.ap-northeast-2.amazonaws.com/" + pet.getProfilePhoto();
    }

    private String convertDate(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
