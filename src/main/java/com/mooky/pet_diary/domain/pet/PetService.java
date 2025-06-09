package com.mooky.pet_diary.domain.pet;

import org.springframework.stereotype.Service;

import com.mooky.pet_diary.domain.pet.dto.PetDto;
import com.mooky.pet_diary.global.exception.AuthException;
import com.mooky.pet_diary.global.exception.NotFoundException;

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
                .orElseThrow(() -> NotFoundException.resource("pet", "no pet with id: " + petId, petId.toString()));

        this.checkIfOwnerIdMatches(currentUserId, savedPet);

        return PetDto.fromEntity(savedPet);
    }

    @Transactional
    public PetDto updatePet(Long petId, PetDto petDto, Long currentUserId) {
        int updatedRow = this.petRepository.updatePet(petId, currentUserId, petDto);
        if (updatedRow != 1) {
            throw AuthException.invalidCredentials("petId=" + petId + " userId=" + currentUserId, "pet not found or access denied");
        }
        return petDto;
    }

    private void checkIfOwnerIdMatches(Long currentUserId, Pet pet) {
        if (!pet.getOwnerId().equals(currentUserId)) {
            throw AuthException.invalidCredentials("petId=" + pet.getId() + " userId=" + currentUserId, null);
        }
    }

}
