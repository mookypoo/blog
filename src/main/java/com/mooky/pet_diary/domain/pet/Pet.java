package com.mooky.pet_diary.domain.pet;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;


@Entity
@Table
public class Pet {
    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    private String name;

    private String species;

    private String breed;

    private LocalDate birthDate;

    private LocalDate adoptionDate;

    private String description;

    private String profilePhoto;

    // create pet
    // TODO catch null pointer exception
    public Pet(PetDto petDto, Long ownerId) {
        this.ownerId = Objects.requireNonNull(ownerId, "Owner ID cannot be null");
        this.name = Objects.requireNonNull(petDto.getName(), "pet name cannot be null");
        this.species = petDto.getSpecies();
        this.breed = petDto.getBreed();
        this.birthDate = this.parseDate(petDto.getBirthDate());
        this.adoptionDate = this.parseDate(petDto.getAdoptionDate());
        this.description = petDto.getDescription();
        this.profilePhoto = petDto.getProfilePhoto();
    }

    private LocalDate parseDate(String dateString) {
        return dateString != null && !dateString.isBlank() ? LocalDate.parse(dateString) : null;
    }
}