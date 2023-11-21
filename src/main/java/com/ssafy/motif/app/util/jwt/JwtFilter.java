package com.ssafy.motif.app.util.jwt;

import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.domain.dto.jwt.TokenDto;
import com.ssafy.motif.app.domain.mapper.RefreshTokenMapper;
import com.ssafy.motif.app.exception.token.UnauthorizedAccessException;
import com.ssafy.motif.app.util.cookie.CookieUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;
    private final RefreshTokenMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        log.debug("요청 URL : {}", request.getRequestURI());

        /* 회원가입 혹은 로그인은 필터 패스 */
        if (request.getRequestURI().equals("/api/v1/member/signup")
            || request.getRequestURI().equals("/api/v1/member/login")) {

            filterChain.doFilter(request, response);
            return;
        }

        /* 토큰 추출 */
        String accessToken = extractToken("Access_Token", request);
        String refreshToken = extractToken("Refresh_Token", request);


        /* (상황1) 토큰이 둘 다 존재, */
        if (accessToken != null && refreshToken != null) {
            String email = jwtProvider.extractEmail(accessToken);
            String refreshTokenOld = getRefreshTokenOld(email);

            // 추출한 토큰과 DB에 있던 토큰 불일치,
            verifyTokenMatch(refreshToken, refreshTokenOld);

            // Access-Token 유효X ?
            if (!jwtProvider.verifyToken(accessToken)) {

                // Access-Token 유효X / Refresh-Token 유효X ?
                if (!jwtProvider.verifyToken(refreshToken)) {
                    throw new IllegalArgumentException("토큰 정보가 만료되었습니다.");
                }

                // Access-Token 유효X / Refresh-Token 유효O
                else {
                    reissue(email, response);
                    setAuthentication(email, request);
                }
            }
            // Access-Token 유효O / Refresh-Token 유효O
            else {
                setAuthentication(email, request);
            }

            filterChain.doFilter(request, response);
            return;
        }

        /* (상황2) 액세스 토큰이 없을 경우, */
        if (accessToken == null && refreshToken != null) {

            String email = jwtProvider.extractEmail(refreshToken);
            String refreshTokenOld = getRefreshTokenOld(email);

            // 추출한 토큰과 DB에 있던 토큰 불일치,
            verifyTokenMatch(refreshToken, refreshTokenOld);

            // Refresh-Token 유효X ?
            if (!jwtProvider.verifyToken(refreshToken)) {
                throw new IllegalArgumentException("토큰 정보가 만료되었습니다.");
            }

            // Access-Token 유효O / Refresh-Token 유효O
            else {
                reissue(email, response);
                setAuthentication(email, request);
            }

            filterChain.doFilter(request, response);
            return;
        }

        /* (상황3) 리프레쉬 토큰이 없을 경우, */
        if (accessToken != null && refreshToken == null) {

            // Access-Token 유효X ?
            if (!jwtProvider.verifyToken(accessToken)) {
                throw new IllegalArgumentException("토큰 정보가 만료되었습니다.");
            }

            // Access-Token 유효O
            else {
                String email = jwtProvider.extractEmail(accessToken);
                reissue(email, response);
                setAuthentication(email, request);
            }

            filterChain.doFilter(request, response);
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    private static void verifyTokenMatch(String refreshToken, String refreshTokenOld) {
        if (!refreshToken.equals(refreshTokenOld)) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다.");
        }
    }

    public void setAuthentication(String email, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                email,
                "",
                Collections.singleton(new SimpleGrantedAuthority("USER"))
            );

        /* 웹 인증 세부 정보를 생성 */
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void reissue(String email, HttpServletResponse response) {
        TokenDto tokenDto = jwtProvider.generateTokenDto(email);

        /* 쿠키에 액세스 토큰 추가 */
        cookieUtil.setCookie(
            "Access_Token",
            tokenDto.getAccessToken(),
            jwtProvider.getAccessTokenTime(), response
        );

        /* 쿠키에 리프레쉬 토큰 추가 */
        cookieUtil.setCookie(
            "Refresh_Token",
            tokenDto.getRefreshToken(),
            jwtProvider.getRefreshTokenTime(), response
        );

        /* 리프레쉬 토큰 갱신 */
        updateRefreshToken(email, tokenDto.getRefreshToken());
    }

    @Transactional
    public void updateRefreshToken(String email, String refreshToken) {
        mapper.updateToken(
            refreshToken,
            email,
            LocalDateTime.now().plusSeconds(jwtProvider.getRefreshTokenTime())
        );
    }

    @Transactional(readOnly = true)
    public String getRefreshTokenOld(String email) {
        return mapper.getTokenValue(email).orElseThrow(
            () -> new IllegalArgumentException("로그인 정보가 유효하지 않습니다.")
        );
    }


    private String extractToken(String tokenType, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                .filter(cookie -> tokenType.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        }
        return null;
    }
}
