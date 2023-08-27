package com.example.rcp1.global.config.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String getUserEmail(String token, String secretkey) {
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token)
                .getBody().get("email", String.class);
    }

    public static boolean isExpired(String token, String secretkey) {
        System.out.println("token = " + token);
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public static String createJwt(String email, String secretKey, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
