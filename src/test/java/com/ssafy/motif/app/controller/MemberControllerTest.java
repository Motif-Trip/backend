package com.ssafy.motif.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.motif.app.domain.dto.member.LoginRequestDto;
import com.ssafy.motif.app.domain.dto.member.SignupRequestDto;
import com.ssafy.motif.app.domain.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    MemberMapper mapper;

    @Autowired
    PasswordEncoder encoder;

    private SignupRequestDto dummyMember;

    @BeforeEach
    void init() {
        dummyMember = SignupRequestDto.builder()
            .email("motif@naver.com")
            .password(encoder.encode("123123"))
            .nickname("Motif")
            .build();
        mapper.signup(dummyMember);
    }


    @Test
    @DisplayName("회원가입 - 성공")
    public void 회원가입1() throws Exception {

        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("123123");
        requestDto.setNickname("HongGilDong");

        String data = om.writeValueAsString(requestDto);

        //when
        final ResultActions result =
            mvc.perform(post("/api/v1/member/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(data))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.email").value(requestDto.getEmail()));
    }

    @Test
    @DisplayName("로그인 - 성공")
    public void 로그인1() throws Exception {

        // given
        LoginRequestDto requestDto = LoginRequestDto.builder()
            .email("motif@naver.com")
            .password("123123")
            .build();

        String data = om.writeValueAsString(requestDto);

        // when
        ResultActions result = mvc.perform(post("/api/v1/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
            .andDo(print());

        // then
        result.andExpect(status().isOk())
            .andExpect(cookie().exists("Access_Token"))
            .andExpect(cookie().exists("Refresh_Token"));
    }

}