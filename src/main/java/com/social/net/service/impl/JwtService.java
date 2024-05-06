package com.social.net.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "YAOmGqqMFI2T2SJqIw7WV1h+V5miq9zS7uF8f/kBBqx55YqAwWhJTCLxLaxwKsRg";

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userdetails) {

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userdetails.getUsername())
                // .issuedAt(new Date(System.currentTimeMillis()))
                // .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 120))
                .id(UUID.randomUUID().toString())
                .signWith(getSignInKey(), Jwts.SIG.HS256).compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userId = extractUserId(token);
        // final boolean isTokenExpired = extractClaim(token,
        // Claims::getExpiration).before(new Date());
        return (userId.equals(userDetails.getUsername()));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
