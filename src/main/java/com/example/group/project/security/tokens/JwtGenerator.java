package com.example.group.project.security.tokens;
import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtGenerator {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final String ENCODED_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getEncoded());

    private static final long EXPIRATION_TIME = 864_000_000;

    public static String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, ENCODED_KEY)
                .compact();
    }

    public static String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(ENCODED_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        log.info("Validating token");
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return Jwts.parser()
                .setSigningKey(ENCODED_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}
