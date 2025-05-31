package com.mooky.pet_diary.global.util;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.pet_diary.global.ApiResponse;
import com.mooky.pet_diary.global.security.CurrentUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${mooky.endpoint}/v1/s3")
@RequiredArgsConstructor
public class S3Controller {
    
    private final S3Service s3Service;

    /**
     * returns one presignedUrl for now, but will change as app grows 
     * <p>
     * query parameters: 
     * <p> type: pet-profile
     * <p>
     * front end returns the full key 
     */
    @PostMapping("/presigned-url")
    public ApiResponse getPresignedUrl(
            @Valid @RequestBody S3UploadReq s3UploadReq,
            @CurrentUser Long userId,
            @RequestParam String type) {
        String presignedUrl = this.s3Service.createPresignedUrl(s3UploadReq, type, userId);
        return ApiResponse.ok(Map.of("presignedUrl", presignedUrl));
    }

}
