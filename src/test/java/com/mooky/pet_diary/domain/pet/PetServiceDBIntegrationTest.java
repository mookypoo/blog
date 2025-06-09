package com.mooky.pet_diary.domain.pet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.mooky.pet_diary.DBIntegrationTestHelper;
import com.mooky.pet_diary.domain.pet.dto.PetDto;
import com.mooky.pet_diary.domain.user.User;
import com.mooky.pet_diary.domain.user.repository.UserRepository;
import com.mooky.pet_diary.global.exception.NotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
public class PetServiceDBIntegrationTest extends DBIntegrationTestHelper {
    
    private @Autowired PetService petService;
    private @Autowired PetRepository petRepository;
    private @Autowired UserRepository userRepository;

    private final PetDto testPetDto = PetDto.builder().name("Test Pet").species("dog").build();

    @Test
    public void createPet_NonExistentUserId_throwException() {
        assertThatThrownBy(() -> this.petService.createPet(this.testPetDto, 999L))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasRootCauseInstanceOf(SQLIntegrityConstraintViolationException.class);
    }

    @Test
    @Transactional
    public void createPet_returnPetDto() {
        User savedUser = super.createAndSaveTestUser(this.userRepository);

        PetDto savedPet = this.petService.createPet(this.testPetDto, savedUser.getId());

        assertThat(savedPet).isNotNull();
        assertThat(savedPet.getPetId()).isGreaterThan(0);
    }

    @Test
    public void updatePet_NonExistentPet_throwException() {
        assertThatThrownBy(() -> this.petService.updatePet(999L, this.testPetDto, 999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("pet_not_found");
    }

    @Test
    @Transactional
    public void updatePet_OwnerIdDoesNotMatch_throwException() {
        User savedUser = super.createAndSaveTestUser(this.userRepository);

        Pet savedPet = this.petRepository.save(Pet.fromPetDto(this.testPetDto, savedUser.getId()));
        PetDto newDto = PetDto.builder()
                .petId(savedPet.getId())
                .name(savedPet.getName())
                .birthDate(LocalDate.parse("2025-05-06"))
                .build();

        assertThatThrownBy(() -> this.petService.updatePet(savedPet.getId(), newDto, 100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("pet_not_found");
    }

    @Test
    @Transactional
    public void updatePet_ReturnPetDto() {
        User savedUser = super.createAndSaveTestUser(this.userRepository);

        Pet savedPet = this.petRepository.save(Pet.fromPetDto(this.testPetDto, savedUser.getId()));
        PetDto newDto = PetDto.builder()
                .petId(savedPet.getId())
                .name(savedPet.getName())
                .birthDate(LocalDate.parse("2025-05-06"))
                .build();

        PetDto updatedPet = this.petService.updatePet(savedPet.getId(), newDto, savedUser.getId());

        assertThat(updatedPet.getPetId()).isEqualTo(savedPet.getId());
        assertThat(updatedPet.getBirthDate()).isEqualTo(LocalDate.parse("2025-05-06"));
    }

}
