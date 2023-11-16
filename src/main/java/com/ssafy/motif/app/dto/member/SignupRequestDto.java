package com.ssafy.motif.app.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private Long memberId;
    private String email;
    private String password;
    private String username;
    private String nickname;

}
