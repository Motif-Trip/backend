package com.ssafy.motif.app.mapper;


import static org.assertj.core.api.Assertions.assertThat;

import com.ssafy.motif.app.domain.dto.member.SignupRequestDto;
import com.ssafy.motif.app.domain.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberMapperTest {

    @Autowired
    MemberMapper mapper;

    @Test
    @DisplayName("회원가입 테스트")
    public void 회원가입() throws Exception {
        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("123123");
        requestDto.setUsername("홍길동");
        requestDto.setNickname("HongGilDong");

        // when
        mapper.signup(requestDto);

        // then
        assertThat(requestDto.getMemberId()).isGreaterThan(0L);
    }


    @Test
    @DisplayName("이메일 유효성 검사")
    public void 이메일_유효성_검사() throws Exception {
        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("123123");
        requestDto.setUsername("홍길동");
        requestDto.setNickname("HongGilDong");

        // when
        mapper.signup(requestDto);

        // then
        assertThat(mapper.isEmailAlreadyInUse(requestDto.getEmail())).isTrue();
    }

    @Test
    @DisplayName("회원가입 직후 DTO 응답조회")
    public void DTO조회() throws Exception {
        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("123123");
        requestDto.setUsername("홍길동");
        requestDto.setNickname("HongGilDong");

        // when
        mapper.signup(requestDto);
        Long responseId = requestDto.getMemberId();

        // then
        assertThat(mapper.findById(responseId).getEmail()).isEqualTo(requestDto.getEmail());
    }

}