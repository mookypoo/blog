package com.mooky.pet_diary.global.security;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mooky.pet_diary.domain.user.User;
import com.mooky.pet_diary.global.exception.ApiException.AuthException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;

// TODO refresh and access token
@Slf4j
@Service
public class JwtService {

    @Value("${mooky.jwt.secret-key}")
    private String secretKey;

    private final String issuer = "mooky-pet-diary";

    private SecretKey getSigningKey() {
        // (1) this.secretKey.getBytes()) 로 하면 alg = hs512
        // (2) Decoders.BASE64.decode(this.secretKey) = alg = hs384
        return Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String generateToken(User user) {
        String token = Jwts.builder().claims().add("userId", user.getId())
                .issuedAt(Date.from(Instant.now()))
                .issuer(this.issuer)
                .and()
                .header().add("typ", "JWT")
                .and()
                .signWith(this.getSigningKey())
                .compact();
        log.debug("token generated: {}", token);
        return token;
    }
    
    private Jws<Claims> parseAccessToken(String accessToken) {
        Jws<Claims> parsed;
        try {
            parsed = Jwts.parser().verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(accessToken);
        } catch (Exception e) {
            throw new AuthException("auth_error", e.getLocalizedMessage(), accessToken,
                    "jwt_token_error");
        }
        if (!parsed.getPayload().getIssuer().equals(this.issuer)) {
            throw new AuthException("auth_error", "there was a problem identifying you", accessToken,
                    "jwt_token_error");
        }
        return parsed;
    }

    public Long getUserIdFromAccessToken(String accessToken) {
        Claims claims = this.parseAccessToken(accessToken).getPayload();
        if (claims.containsKey("userId")) {
            return Long.parseLong(claims.get("userId").toString());
        }
        return null;

    }
    

}
