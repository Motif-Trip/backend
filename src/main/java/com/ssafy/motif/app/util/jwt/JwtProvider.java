package com.ssafy.motif.app.util.jwt;

import com.ssafy.motif.app.dto.jwt.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("classpath:application-secret.yml")
public class JwtProvider {

    @Value("${jwt.ACCESS_TIME}")
    private int ACCESS_TOKEN_TIME;

    @Value("${jwt.REFRESH_TIME}")
    private int REFRESH_TOKEN_TIME;

    @Value("${jwt.SECRET_KEY}")
    private String SECRET_KEY;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    }

    /**
     * @param email 유저의 이메일
     * @return accessToken 과 refreshToken 이 담긴 token 객체를 리턴한다.
     */
    public TokenDto generateTokenDto(String email) {
        return TokenDto.builder()
            .accessToken(createAccessToken(email))
            .refreshToken(createRefreshToken(email))
            .email(email)
            .build();
    }

    /**
     * @param email - 회원 이메일
     * @return ACCESS_TOKEN 발급
     */
    private String createAccessToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME * 1000))
            .signWith(key)
            .compact();
    }

    /**
     * @param email - 회원 이메일
     * @return REFRESH_TOKEN 발급
     */
    private String createRefreshToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME * 1000))
            .signWith(key)
            .compact();
    }

    /**
     * @param token -> JWT claims
     */
    private Claims parseClaims(String token) {
        try {
            Claims body = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
            return body;
        } catch (ExpiredJwtException e) {
            log.warn("========== 만료된 토큰 : {}", token);
            return e.getClaims();
        }
    }


    /**
     * @param token 검증 메서드
     */
    protected Boolean verifyToken(String token) {
        try {
            parseClaims(token);
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error("========== 잘못된 형식의 토큰 : {}", e.getMessage());
            throw new IllegalArgumentException("로그인 정보 형식이 올바르지 않습니다.");
        } catch (SignatureException e) {
            log.error("========== 토큰 서명 문제 : {}", e.getMessage());
            throw new IllegalArgumentException("로그인 정보가 변경되었습니다.");
        }
        return true;
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public Date extractExpirationAt(String token) {
        return parseClaims(token).getExpiration();
    }

    public int getAccessTokenTime() {
        return ACCESS_TOKEN_TIME;
    }

    public int getRefreshTokenTime() {
        return REFRESH_TOKEN_TIME;
    }

}
