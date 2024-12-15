package com.example.group.project.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = System.getenv("SECURE_KEY");

    private static final long EXPIRATION_TIME = 864_000_000;

    public static String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJwt(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}
