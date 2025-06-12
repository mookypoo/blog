package com.mooky.pet_diary.domain.health_entry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mooky.pet_diary.domain.health_entry.dto.OptionDto;
import com.mooky.pet_diary.domain.health_entry.dto.PetHealthEntryForm;

@Repository
public interface HealthEntryRepository extends JpaRepository<HealthEntry, Long> {
    
    // benefits of constructor expression --> compile time check
    @Query("""
            SELECT NEW com.mooky.pet_diary.domain.health_entry.dto.OptionDto(el.value, el.display, el.sortOrder)
            FROM EnergyLevel el
            WHERE el.isActive = true
            ORDER BY el.sortOrder
            """)
    public List<OptionDto> findActiveEnergyLevels();

    @Query("""
            SELECT NEW com.mooky.pet_diary.domain.health_entry.dto.OptionDto(at.value, at.display, at.sortOrder)
            FROM ActivityType at
            WHERE at.isActive = true
            ORDER BY at.sortOrder
            """)
    public List<OptionDto> findActiveActivityTypes();

    @Query("""
            SELECT NEW com.mooky.pet_diary.domain.health_entry.dto.PetHealthEntryForm(p.id, p.name, p.weight, p.weightUnit)
            FROM Pet p
            WHERE p.id=:petId AND p.ownerId=:ownerId     
            """)
    Optional<PetHealthEntryForm> findPetHealthFormData(@Param("petId") Long petId, @Param("ownerId") Long userId);
}
