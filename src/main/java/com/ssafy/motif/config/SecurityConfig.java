package com.ssafy.motif.config;

import com.ssafy.motif.app.domain.mapper.RefreshTokenMapper;
import com.ssafy.motif.app.handler.TokenExceptionFilterHandler;
import com.ssafy.motif.app.util.cookie.CookieUtil;
import com.ssafy.motif.app.util.jwt.JwtFilter;
import com.ssafy.motif.app.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CookieUtil cookieUtil;
    private final JwtProvider JwtProvider;
    private final RefreshTokenMapper mapper;
    private final TokenExceptionFilterHandler tokenExceptionFilterHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .antMatchers("/v2/api-docs")
            .antMatchers("/v3/api-docs")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/webjars/**")
            .antMatchers("/swagger/**")
            .antMatchers("/api-docs/**")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/swagger-resources/**")
            ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security
            .httpBasic().disable()
            .csrf().disable()
            .cors().disable();

        security
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security
            .authorizeRequests()
            .antMatchers(
                "/api/v1/member/signup",
                "/api/v1/member/login",
                "/api/v1/data"
            ).permitAll()
            .anyRequest().authenticated();

        security
            .addFilterBefore(tokenExceptionFilterHandler,
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtFilter(JwtProvider, cookieUtil, mapper),
                UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
