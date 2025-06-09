package com.mooky.pet_diary.domain.user.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mooky.pet_diary.domain.pet.dto.PetSummaryDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@RequiredArgsConstructor
public class UserProfileDto {
    
    private final Long userId;
    private final String username;
    private final String email;
    private final List<PetSummaryDto> pets;

}
