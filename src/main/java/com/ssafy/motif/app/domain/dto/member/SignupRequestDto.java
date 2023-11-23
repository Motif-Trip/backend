package com.ssafy.motif.app.domain.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @JsonIgnore
    private Long memberId;

    @Email(message = "이메일 형식이 유효하지 않습니다")
    @NotBlank(message = "이메일은 필수 항목입니다")
    @Schema(description = "이메일", example = "motif000@trip.com", required = true)
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
    @Schema(description = "비밀번호", example = "123123", required = true)
    private String password;

    @NotBlank(message = "닉네임은 필수 항목입니다")
    @Schema(description = "닉네임", example = "모티브000", required = true)
    private String nickname;

}
