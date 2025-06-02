package com.mooky.pet_diary.domain.pet;

import java.time.LocalDate;

import com.mooky.pet_diary.domain.pet.dto.PetDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Setter Long id;

    private Long ownerId;

    private String name;

    private String species;

    private String breed;

    private LocalDate birthDate;

    private LocalDate adoptionDate;

    private String description;

    private String profilePhoto;

    public static Pet fromPetDto(PetDto petDto, Long ownerId) {
        return new PetBuilder()
                .ownerId(ownerId)
                .name(petDto.getName())
                .species(petDto.getSpecies())
                .breed(petDto.getBreed())
                .birthDate(petDto.getBirthDate())
                .adoptionDate(petDto.getAdoptionDate())
                .description(petDto.getDescription())
                .profilePhoto(petDto.getProfilePhoto())
                .build();
    }
   
}