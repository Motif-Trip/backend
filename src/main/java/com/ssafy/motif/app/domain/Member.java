package com.ssafy.motif.app.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private Long memberId;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String profileImg;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
