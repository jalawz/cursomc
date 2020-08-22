package com.cursomc.security;

import java.util.Date;
import java.util.Objects;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken (final String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public Boolean tokenValido (final String token) {
        final Claims claims = getClaims(token);

        if (!Objects.isNull(claims)) {
            final String username = claims.getSubject();
            final Date expirationDate = claims.getExpiration();
            final Date now = new Date(System.currentTimeMillis());

            if (!Objects.isNull(username) && !Objects.isNull(expirationDate) && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername (final String token) {
        final Claims claims = getClaims(token);

        return Objects.isNull(claims) ? null : claims.getSubject();
    }

    private Claims getClaims (final String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (final Exception e) {
            return null;
        }
    }
}
