package com.ssafy.motif.app.handler;

import com.ssafy.motif.app.exception.token.UnauthorizedAccessException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenExceptionFilterHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedAccessException e) {
            response.setStatus(404);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String body = "{\n"
                + "              \"status\" : \"FAILED\",\n"
                + "              \"message\" : \"로그인이 필요한 서비스입니다.\"\n"
                + "         }";
            response.getWriter().write(body);
        }
    }
}
