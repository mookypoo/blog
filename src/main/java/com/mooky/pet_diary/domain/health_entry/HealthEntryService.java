package com.mooky.pet_diary.domain.health_entry;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mooky.pet_diary.domain.health_entry.dto.HealthEntryFormDto;
import com.mooky.pet_diary.domain.health_entry.dto.OptionDto;
import com.mooky.pet_diary.domain.health_entry.dto.PetHealthEntryForm;
import com.mooky.pet_diary.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthEntryService {

    private final HealthEntryRepository heRepository;
    
    public HealthEntryFormDto getHealthEntryForm() {
        List<OptionDto> energyLevels = this.heRepository.findActiveEnergyLevels();
        List<OptionDto> activityTypes = this.heRepository.findActiveActivityTypes();
        return new HealthEntryFormDto(energyLevels, activityTypes);
    }

    public PetHealthEntryForm getPetHealthEntryFormData(Long petId, Long userId) {
        PetHealthEntryForm data = this.heRepository.findPetHealthFormData(petId, userId).orElseThrow(
                () -> NotFoundException.matchingPetAndOwner(petId, userId));
        return data;
    }
    
}
