package com.mooky.pet_diary.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class S3UrlUtil {

    @Value("${aws.s3.base-url}")
    private String baseUrl;

    private static String s3BaseUrl;

    @PostConstruct
    public void init() {
        s3BaseUrl = this.baseUrl;
    }

    public static String buildUrl(String key) {
        return key == null || key.trim().isEmpty()
            ? null  
            : s3BaseUrl + "/" + key.trim();
    }
}
