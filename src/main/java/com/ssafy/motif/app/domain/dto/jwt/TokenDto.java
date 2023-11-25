package com.ssafy.motif.app.domain.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    private String email;
    private String accessToken;
    private String refreshToken;

}
