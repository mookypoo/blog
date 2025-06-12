package com.mooky.pet_diary.domain.health_entry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@Entity
@Table
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class EnergyLevel {

    @Id private final Integer id;
    private final String value;
    private final String display;
    private final Integer sortOrder;
    private final Boolean isActive;

}
