package com.mooky.pet_diary.domain.pet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mooky.pet_diary.global.util.S3UrlUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Getter
public class PetSummaryDto {
    private final Long petId;
    private final String name;
    private final @JsonIgnore String profilePhoto;

    public String getProfilePhotoUrl() {
        return S3UrlUtil.buildUrl(this.profilePhoto);
    }
}
