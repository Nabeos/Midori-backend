package com.midorimart.managementsystem.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.TokenPayload;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    private final String secret="ABCDEFGHI";
    public String generateToken(User user, long expiredDate){
        Map<String, Object> claims=new HashMap<>();

        TokenPayload tokenPayload=TokenPayload.builder()
        .userId(user.getId())
        .email(user.getEmail()).build();

        claims.put("payload",tokenPayload);

        return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+expiredDate*1000))
        .signWith(SignatureAlgorithm.HS512, secret).compact();

    }
}
