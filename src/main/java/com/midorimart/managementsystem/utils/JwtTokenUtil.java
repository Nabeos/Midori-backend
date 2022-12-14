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
    private final String secret = "ABCDEFGHI";

    // Create token when login
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

    // Get payload from token
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

    // Validate token
    public boolean validateToken(String token, TokenPayload tokenPayload) {
        return getPayLoadFromToken(token).equals(tokenPayload) && !isExpiredToken(token);
    }

    // Check expired token
    private boolean isExpiredToken(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    // Get expiration date
    private Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
