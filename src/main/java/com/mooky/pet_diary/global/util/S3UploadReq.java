package com.mooky.pet_diary.global.util;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
public class S3UploadReq {

    @NotBlank
    private final String fileName;

    @NotBlank
    private final String fileType;
}
