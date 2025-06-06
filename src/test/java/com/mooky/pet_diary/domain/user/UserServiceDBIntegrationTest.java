package com.mooky.pet_diary.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.mooky.pet_diary.domain.pet.Pet;
import com.mooky.pet_diary.domain.pet.PetRepository;
import com.mooky.pet_diary.domain.user.User.SignUpType;
import com.mooky.pet_diary.domain.user.dto.UserProfileDto;
import com.mooky.pet_diary.domain.user.repository.UserRepository;
import com.mooky.pet_diary.global.exception.ApiException.NotFoundException;

@Testcontainers
@SpringBootTest
public class UserServiceDBIntegrationTest {

    private @Autowired UserRepository userRepository;
    private @Autowired PetRepository petRepository;
    private @Autowired UserService userService;

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
    public void getUserProfile_NonExistentUserId_throwException() {
        assertThatThrownBy(() -> this.userService.getUserProfile(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("user_not_found");
    }

    @Test
    @Rollback
    public void getUserProfile_NoPet_ReturnProfileDto() {
        User testUser = this.createAndSaveTestUser();
        UserProfileDto profile = this.userService.getUserProfile(testUser.getId());

        assertThat(profile.getUserId()).isEqualByComparingTo(testUser.getId());
        assertThat(profile.getPets()).hasSize(0);
    }

    @Test
    @Rollback
    public void getUserProfile_HasPets_ReturnProfileDto() {
        User testUser = this.createAndSaveTestUser();
        this.petRepository.saveAll(List.of(
                Pet.builder().name("Test Pet 1").ownerId(testUser.getId()).build(),
                Pet.builder().name("Test Pet 2").ownerId(testUser.getId()).build()
            ));

        UserProfileDto profile = this.userService.getUserProfile(testUser.getId());

        assertThat(profile.getUserId()).isEqualByComparingTo(testUser.getId());
        assertThat(profile.getPets()).hasSize(2);
    }
}