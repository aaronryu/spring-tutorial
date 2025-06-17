package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    public static final String AUTHORITIES_KEY = "roles";

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesFromToken(Claims claims) {
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        return authorities;
    }

    @Override
    // JwtParser.parse method can throw below exception, so you should catch and do something.
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(((JwtAuthenticationToken) authentication).getJsonWebToken())
                    .getPayload();
        } catch (SignatureException signatureException) {
            // SignatureException – if a JWS signature was discovered, but could not be verified. JWTs that fail signature validation should not be trusted and should be discarded.
            throw new JwtInvalidException("signature key is different", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            // ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
            throw new JwtInvalidException("expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            // MalformedJwtException – if the specified JWT was incorrectly constructed (and therefore invalid). Invalid JWTs should not be trusted and should be discarded.
            throw new JwtInvalidException("malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // IllegalArgumentException – if the specified string is null or empty or only whitespace.
            throw new JwtInvalidException("using illegal argument like null", illegalArgumentException);
        }
        return new JwtAuthenticationToken(claims.getSubject(), null, getAuthoritiesFromToken(claims));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}