package com.mooky.pet_diary.domain.user.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mooky.pet_diary.global.exception.AuthException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleTokenService {
    
    @Value("${mooky.google.client-id}")
    private String googleClientId;

    public Payload verifyGoogleIdToken(String idToken) {
        log.debug("google client id: {}, idToken: {}", this.googleClientId, idToken);
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
            GsonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(this.googleClientId))
            .build();
        Payload googlePayload = null;

        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken != null) {
                googlePayload = googleIdToken.getPayload();
            } else {
                throw AuthException.invalidLogin("잘못된 google idToken 입니다", idToken, "invalid_google_id_token");
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return googlePayload;
    }

}
