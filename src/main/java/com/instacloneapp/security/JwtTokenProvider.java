package com.instacloneapp.security;

import com.instacloneapp.entities.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryToLogOut = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claimMapToToken = new HashMap<>();
        claimMapToToken.put("id", userId);
        claimMapToToken.put("username", user.getEmail());
        claimMapToToken.put("firstName", user.getFirstName());
        claimMapToToken.put("lastName", user.getLastName());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimMapToToken)
                .setIssuedAt(now)
                .setExpiration(expiryToLogOut)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;

        } catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException exception) {

            log.error(exception.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();

        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }
}
