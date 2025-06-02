package com.mooky.pet_diary.domain.pet;

import org.springframework.stereotype.Service;

import com.mooky.pet_diary.domain.pet.dto.PetDto;
import com.mooky.pet_diary.global.exception.ApiException.AuthException;
import com.mooky.pet_diary.global.exception.ApiException.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {
    
    private final PetRepository petRepository;

    public PetDto createPet(PetDto petDto, Long currentUserId) {
        Pet savedPet = this.petRepository.save(Pet.fromPetDto(petDto, currentUserId));
        return PetDto.fromEntity(savedPet);
    }

    public PetDto getPetDetails(Long petId, Long currentUserId) {
        Pet savedPet = this.petRepository.findById(petId)
                .orElseThrow(() -> new NotFoundException("pet_not_found", "no pet with id: " + petId, petId.toString(),
                        null));

        this.checkIfOwnerIdMatches(currentUserId, savedPet);

        return PetDto.fromEntity(savedPet);
    }
    
    @Transactional
    public PetDto updatePetUsingQuery(Long petId, PetDto petDto, Long currentUserId) {
        int updatedRow = this.petRepository.updatePet(petId, currentUserId, petDto);
        if (updatedRow != 1) {
            throw new NotFoundException("invalid_data", "pet not found or access denied",
                    "petId=" + petId + " userId=" + currentUserId, null);
        }
        return petDto;
    }

    private void checkIfOwnerIdMatches(Long currentUserId, Pet pet) {
        if (!pet.getOwnerId().equals(currentUserId)) {
            throw new AuthException("auth_exception",
                    "you are not authorized to view this pet",
                    "petId=" + pet.getId() + " userId=" + currentUserId, null);
        }
    }

}
