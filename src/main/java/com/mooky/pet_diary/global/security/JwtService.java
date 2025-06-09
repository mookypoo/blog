package com.mooky.pet_diary.global.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.mooky.pet_diary.global.exception.AuthException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * for development: access token lasts 7 days and refresh token lasts 30 days
 * <p>
 * access token: claim "type" is access
 * <p>
 * refresh token: claim "type" is refresh
 * 
 * @throws AuthException
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final String accessTokenType = "access";
    private final String refreshTokenType = "refresh";

    private SecretKey getSigningKey() {
        // (1) this.secretKey.getBytes()) 로 하면 alg = hs512
        // (2) Decoders.BASE64.decode(this.secretKey) = alg = hs384
        return Keys.hmacShaKeyFor(this.jwtProperties.getSecretKey().getBytes());
    }

    public String generateAccessToken(Long userId) {
        return this.generateToken(userId, this.accessTokenType, this.jwtProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(Long userId) {
        return this.generateToken(userId, this.refreshTokenType, this.jwtProperties.getRefreshTokenExpiration());
    }

    private String generateToken(Long userId, String type, long expiration) {
        Date now = new Date();
        String token = Jwts.builder().claims()
                .add("userId", userId)
                .add("type", type)
                .issuedAt(now)
                .issuer(this.jwtProperties.getIssuer())
                .expiration(new Date(now.getTime() + expiration))
                .and()
                .header().add("typ", "JWT")
                .and()
                .signWith(this.getSigningKey())
                .compact();
        log.debug("generate token userId={}", userId);
        return token;
    }
    
    private Jws<Claims> parseAccessToken(String accessToken) {
        Jws<Claims> parsed;
        try {
            parsed = Jwts.parser().verifyWith(this.getSigningKey())
                    .build()
                    .parseSignedClaims(accessToken);
        } catch (ExpiredJwtException e) {
            throw AuthException.expiredJwtToken(accessToken);
        } catch (JwtException e) {
            throw AuthException.invalidJwtToken(accessToken, e.getMessage());
        } catch (Exception e) {
            throw e;
        }
        
        if (!parsed.getPayload().getIssuer().equals(this.jwtProperties.getIssuer())) {
            throw AuthException.invalidJwtToken(accessToken, "invalid issuer");
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
