package com.mooky.pet_diary.domain.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mooky.pet_diary.domain.pet.dto.PetSummaryDto;
import com.mooky.pet_diary.domain.user.dto.UserProfileDto;
import com.mooky.pet_diary.domain.user.dto.UserWithPetSummaryProjection;
import com.mooky.pet_diary.domain.user.repository.UserRepository;
import com.mooky.pet_diary.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    public UserProfileDto getUserProfile(Long userId) {
        List<UserWithPetSummaryProjection> results = this.userRepository.findUserProfileWithPetsSummaryById(userId);

        if (results.isEmpty()) {
            throw NotFoundException.resource("user", "no such user", userId.toString());
        }

        UserWithPetSummaryProjection first = results.getFirst();
        
        List<PetSummaryDto> petSummaries = results.stream()
                .filter(r -> r.getPetId() != null)
                .map(r -> new PetSummaryDto(r.getPetId(), r.getPetName(), r.getPetProfilePhoto()))
                .toList();

        return new UserProfileDto(first.getUserId(), first.getUsername(), first.getEmail(), petSummaries);
    }
}
