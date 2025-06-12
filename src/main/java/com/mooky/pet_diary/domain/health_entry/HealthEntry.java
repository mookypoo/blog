package com.mooky.pet_diary.domain.health_entry;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table
@Getter
@Builder
public class HealthEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long petId;

    private Long userId;

    private LocalDate entryDate;

    private Float weight;

    private String weightUnit;

    private String energyLevelValue;

    private String energyLevelDisplay;

    private String notes;

}
