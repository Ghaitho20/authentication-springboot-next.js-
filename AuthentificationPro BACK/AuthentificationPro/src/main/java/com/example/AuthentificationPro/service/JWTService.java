package com.example.AuthentificationPro.service;

import com.example.AuthentificationPro.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function; // Import the Function interface

@Service
public class JWTService {

    private String secretkey;

    public JWTService() {
        // Use a fixed-size key for HMAC-SHA256 (minimum 256 bits = 32 bytes)
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        secretkey = Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 30 * 1000)) // 30 hours
                .signWith(getKey()) // Sign the token
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+7*24*60*60*1000))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //Claims input of the function and T is the output desired or the claim (data) that i want to extract
    public <T> T extractClaim(String token, Function<Claims,T> ClaimsResolver) {
        final Claims claims  = extractAllClaims(token);
        return ClaimsResolver.apply(claims);

    }
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    public boolean isTokenValid(String token , User user) {
        final String email = extractEmail(token);
        return (email.equals(user.getEmail()) || !isTokenExpired(token));
    }

    public  boolean isTokenExpired(String token) {
        return extractExpiration( token).before(new Date());

    }







    /*public String validateAndExtractUsername(String refreshToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(refreshToken);



            // Extract the username (subject) from the token
            return claims.getBody().getSubject();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid refresh token", e);
        }
    }*/

}

