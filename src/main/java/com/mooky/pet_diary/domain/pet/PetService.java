package com.mooky.pet_diary.domain.pet;

import org.springframework.stereotype.Service;

import com.mooky.pet_diary.domain.pet.dto.PetDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PetService {
    
    private final PetRepository petRepository;

    public void createPet(PetDto petDto) {
        
    }

}
