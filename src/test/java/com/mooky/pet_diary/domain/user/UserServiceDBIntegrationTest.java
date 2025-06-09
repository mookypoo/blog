package com.mooky.pet_diary.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mooky.pet_diary.DBIntegrationTestHelper;
import com.mooky.pet_diary.domain.pet.Pet;
import com.mooky.pet_diary.domain.pet.PetRepository;
import com.mooky.pet_diary.domain.user.dto.UserProfileDto;
import com.mooky.pet_diary.domain.user.repository.UserRepository;
import com.mooky.pet_diary.global.exception.NotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
public class UserServiceDBIntegrationTest extends DBIntegrationTestHelper {

    private @Autowired UserRepository userRepository;
    private @Autowired PetRepository petRepository;
    private @Autowired UserService userService;

    @Test
    public void getUserProfile_NonExistentUserId_throwException() {
        assertThatThrownBy(() -> this.userService.getUserProfile(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("user_not_found");
    }

    @Test
    @Transactional
    public void getUserProfile_NoPet_ReturnProfileDto() {
        User testUser = super.createAndSaveTestUser(this.userRepository);
        UserProfileDto profile = this.userService.getUserProfile(testUser.getId());

        assertThat(profile.getUserId()).isEqualByComparingTo(testUser.getId());
        assertThat(profile.getPets()).hasSize(0);
    }

    @Test
    @Transactional
    public void getUserProfile_HasPets_ReturnProfileDto() {
        User testUser = super.createAndSaveTestUser(this.userRepository);
        this.petRepository.saveAll(List.of(
                Pet.builder().name("Test Pet 1").ownerId(testUser.getId()).build(),
                Pet.builder().name("Test Pet 2").ownerId(testUser.getId()).build()
            ));

        UserProfileDto profile = this.userService.getUserProfile(testUser.getId());

        assertThat(profile.getUserId()).isEqualByComparingTo(testUser.getId());
        assertThat(profile.getPets()).hasSize(2);
    }
}