package com.mooky.blog.global.security;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mooky.blog.domain.user.User;
import com.mooky.blog.global.exception.ApiException.AuthException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtService {

    @Value("${mooky.jwt.secret-key}")
    private String secretKey;

    private SecretKey getSigningKey() {
        // (1) this.secretKey.getBytes()) 로 하면 alg = hs512
        // (2) Decoders.BASE64.decode(this.secretKey) = alg = hs384
        return Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String generateToken(User user) {
        String token = Jwts.builder().claims().add("userId", user.getId())
                .issuedAt(Date.from(Instant.now()))
                .subject("mooky-blog")
                .and()
                .header().add("typ", "JWT")
                .and()
                .signWith(this.getSigningKey())
                .compact();
        log.debug("token generated: {}", token);
        return token;
    }
    
    private Jws<Claims> parseAccessToken(String accessToken) {
        Jws<Claims> parsed = Jwts.parser().verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(accessToken);
        if (!parsed.getPayload().getSubject().equals("mooky-blog")) {
            throw new AuthException("auth_error", "there was an issue with identifying you", accessToken,
                    "access token subject error");
        }
        return parsed;
    }

    public String getUserIdFromAccessToken(String accessToken) {
        Claims claims = this.parseAccessToken(accessToken).getPayload();
        if (claims.containsKey("userId")) {
            return claims.get("userId").toString();
        }
        return null;

    }
    

}
