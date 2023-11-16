package com.ssafy.motif.app.util.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.ssafy.motif.app.dto.jwt.TokenDto;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    JwtProvider JwtProvider;

    @Test
    @DisplayName("토큰 생성과 검증 테스트")
    public void 토큰_테스트() throws Exception {

        // given
        final String email = "test@test.com";

        // when
        TokenDto tokenDto = JwtProvider.generateTokenDto(email);

        // then
        assertThat(JwtProvider.extractEmail(tokenDto.getAccessToken())).isEqualTo(email);
        assertThat(JwtProvider.extractExpirationAt(tokenDto.getAccessToken())).isAfter(new Date(System.currentTimeMillis()));
        assertThat(JwtProvider.extractExpirationAt(tokenDto.getRefreshToken())).isAfter(new Date(System.currentTimeMillis()));
    }



}