package com.mooky.pet_diary.domain.pet.dto;

import java.time.LocalDate;

import com.mooky.pet_diary.domain.pet.Pet;
import com.mooky.pet_diary.domain.pet.UpdatePet;
import com.mooky.pet_diary.global.util.S3UrlUtil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * when creating pet for the first time and there is a profile photo: 
 * <p>(1) get a presigned url via "/v1/s3/presigned-url"
 * <p>(2) upload on presigned url
 * <p>(3) send only the key back = profilePhoto
 * <p>
 * <b>"profilePhoto"</b> is the key to the s3 object --> need to send this when updating pet (or a new key through the same process as above)
 * <p>
 * <b>"profilePhotoUrl"</b> is the full url for which the user can see the photo
 */
@Builder
@Jacksonized
@Getter
@ToString
public class PetDto {
    @NotNull(message = "pet id is required", groups = UpdatePet.class)
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

    public String getProfilePhotoUrl() {
        return S3UrlUtil.buildUrl(this.profilePhoto);
    }

}
