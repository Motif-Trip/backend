package com.ssafy.motif.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ssafy.motif.app.dto.member.MemberResponseDto;
import com.ssafy.motif.app.dto.member.SignupRequestDto;
import com.ssafy.motif.app.exception.EmailDuplicateException;
import com.ssafy.motif.app.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 - 성공")
    public void 회원가입_성공() throws Exception {
        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("123123");
        requestDto.setUsername("홍길동");
        requestDto.setNickname("HongGilDong");

        // when
        MemberResponseDto responseDto = memberService.signup(requestDto);

        // then
        Assertions.assertThat(responseDto.getEmail()).isEqualTo(requestDto.getEmail());
    }

    @Test
    @DisplayName("회원가입 - 이메일 중복")
    public void 회원가입_실패() throws Exception {
        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("123123");
        requestDto.setUsername("홍길동");
        requestDto.setNickname("HongGilDong");

        SignupRequestDto requestDto2 = new SignupRequestDto();
        requestDto2.setEmail("test@test.com");
        requestDto2.setPassword("123123");
        requestDto2.setUsername("홍길동2");
        requestDto2.setNickname("HongGilDong2");

        // when
        memberService.signup(requestDto);

        // then
        assertThrows(EmailDuplicateException.class, () -> {
            memberService.signup(requestDto2);
        });
    }


}