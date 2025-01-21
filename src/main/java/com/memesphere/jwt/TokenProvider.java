package com.memesphere.jwt;

import com.memesphere.service.user.UserServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 24 * 60 * 60; // access token은 24시간
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 24 * 60 * 60 * 7; // refresh token은 1주일

    private Key key;
    private final UserServiceImpl userServiceImpl;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getLoginId(String token) {
        try {
            key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("claims.getSubject() = {}", claims.getSubject());
            return claims.getSubject();
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new RuntimeException("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new RuntimeException("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new RuntimeException("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new RuntimeException("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new RuntimeException("JWT claims string is empty.", ex);
        }
    }

    public String createAccessToken(String username, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + ACCESS_TOKEN_VALIDITY_SECONDS * 1000);

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(String username) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + REFRESH_TOKEN_VALIDITY_SECONDS * 1000);

        return Jwts.builder()
                .setSubject(username)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String getTokenUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        log.info("Getting authentication for token user ID: {}", getTokenUserId(token));
        UserDetails userDetails = (UserDetails) userServiceImpl.getUserInfo(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new RuntimeException("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new RuntimeException("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new RuntimeException("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new RuntimeException("JWT 토큰이 잘못되었습니다.", e);
        }
    }
}
