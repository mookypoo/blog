package com.mooky.pet_diary.domain.pet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mooky.pet_diary.domain.pet.dto.PetDto;


@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    public void getPetDetails_WhenOwnerMatches_ReturnPetDto() {
        Long petId = 123L;
        Long ownerId = 456L;
        String petName = "Pet Test Name";
        Pet pet = Pet.builder()
                .id(petId)
                .name(petName)
                .ownerId(ownerId)
                .profilePhoto("test/photo.jpeg") // TODO test
                .build();
        
        when(this.petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetDto dto = this.petService.getPetDetails(petId, ownerId);

        assertThat(dto.getName()).isEqualTo(petName);
        assertThat(dto.getPetId()).isEqualTo(petId);
    }

}
