package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements InitializingBean {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private long expiration;

    private SecretKey key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generate(Authentication authentication) {
        String username = authentication.getName();
//      User user = authentication.getPrincipal();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + (expiration * 1000));
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
//              .claim("id", user.getId())
//              .claim("name", user.getName())
//              .claim("job", user.getJob())
//              .claim("specialty", user.getSpecialty())
                .claim(JwtAuthenticationProvider.AUTHORITIES_KEY, authorities)
                .signWith(key)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("id", Integer.class);
    }

    private String getCurrentToken() {
        HttpServletRequest currentRequest = getCurrentRequest();
        if (Objects.nonNull(currentRequest)) {
            return JwtAuthenticationFilter.resolveToken(currentRequest);
        }
        return null;
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attrs)) {
            return attrs.getRequest();
        }
        return null;
    }
}
