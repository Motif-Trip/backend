package com.ssafy.motif.app.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignupRequestDto {

    private Long memberId;
    private String email;
    private String password;
    private String username;
    private String nickname;

}
