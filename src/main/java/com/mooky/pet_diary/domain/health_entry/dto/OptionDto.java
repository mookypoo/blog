package com.mooky.pet_diary.domain.health_entry.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OptionDto {
    private final String value;
    private final String display;
    private final int sortOrder;
}
