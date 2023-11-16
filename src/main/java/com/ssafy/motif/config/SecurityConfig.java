package com.ssafy.motif.config;

import com.ssafy.motif.app.mapper.RefreshTokenMapper;
import com.ssafy.motif.app.util.cookie.CookieUtil;
import com.ssafy.motif.app.util.jwt.JwtFilter;
import com.ssafy.motif.app.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security
            .httpBasic().disable()
            .csrf().disable()
            .cors();

        security
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security
            .authorizeRequests()
            .anyRequest().permitAll();

        security
            .addFilterBefore(new JwtFilter(JwtProvider, cookieUtil, mapper),
                UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
