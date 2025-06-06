package com.mooky.pet_diary.domain.pet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.mooky.pet_diary.domain.pet.dto.PetDto;
import com.mooky.pet_diary.domain.user.User;
import com.mooky.pet_diary.domain.user.User.SignUpType;
import com.mooky.pet_diary.domain.user.repository.UserRepository;
import com.mooky.pet_diary.global.exception.ApiException.NotFoundException;

@SpringBootTest
@Testcontainers
public class PetServiceDBIntegrationTest {

    private @Autowired PetService petService;
    private @Autowired PetRepository petRepository;
    private @Autowired UserRepository userRepository;

    @Container
    static public MariaDBContainer<?> mariaDB = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.5"))
            .withDatabaseName("pet_diary")
            .withUsername("testuser")
            .withPassword("testpassword")
            .withInitScript("schema.sql");

    @DynamicPropertySource
    static public void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDB::getUsername);
        registry.add("spring.datasource.password", mariaDB::getPassword);
    }

    private final PetDto testPetDto = PetDto.builder().name("Test Pet").species("dog").build();

    private User createAndSaveTestUser() {
        User testUser = new User.Builder()
                .email("test@test.com")
                .signupType(SignUpType.EMAIL)
                .password("testpassword")
                .username("testusername")
                .build();
        return this.userRepository.save(testUser);
    }

    @Test
    public void createPet_NonExistentUserId_throwException() {
        assertThatThrownBy(() -> this.petService.createPet(this.testPetDto, 999L))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasRootCauseInstanceOf(SQLIntegrityConstraintViolationException.class);
    }

    @Test
    @Rollback
    public void createPet_returnPetDto() {
        User savedUser = this.createAndSaveTestUser();

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
    @Rollback
    public void updatePet_OwnerIdDoesNotMatch_throwException() {
        User savedUser = this.createAndSaveTestUser();

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
    @Rollback
    public void updatePet_ReturnPetDto() {
        User savedUser = this.createAndSaveTestUser();

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

