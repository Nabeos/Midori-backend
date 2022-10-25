package com.midorimart.managementsystem.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.TokenPayload;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private static final long ACCESS_TOKEN_REQUIRED = 60 * 60 * 2;
    private final String secret = "ABCDEFGHI";

    public String generateToken(User user, long expiredDate) {
        Map<String, Object> claims = new HashMap<>();

        TokenPayload tokenPayload = TokenPayload.builder()
                .userId(user.getId())
                .email(user.getEmail()).build();

        claims.put("payload", tokenPayload);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredDate * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public TokenPayload getPayLoadFromToken(String token) {
        return getClaimFromToken(token, (Claims claims) -> {
            Map<String, Object> result = (Map<String, Object>) claims.get("payload");
            return TokenPayload.builder().userId((int) result.get("userId")).email((String) result.get("email"))
                    .build();
        });
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
