package com.tfg.backend.jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tfg.backend.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
// Service for handling JWT operations
public class JwtService {

    // Secret key for signing JWT tokens
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    // Generates a JWT token for a user
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    // Generates a JWT token with extra claims for a user
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        User currentUser = (User) user;
            
        extraClaims.put("username", currentUser.getUsername());
        extraClaims.put("email", currentUser.getEmail());

        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // Retrieves the secret key
    private Key getKey() {
       byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extracts the username from a JWT token
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Validates if a JWT token is valid for a given user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Retrieves all claims from a JWT token
    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // Retrieves a specific claim from a JWT token
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Retrieves the expiration date from a JWT token
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Checks if a JWT token is expired
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
