package com.ssafy.motif.app.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.motif.app.dto.member.SignupRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
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
    ObjectMapper mapper;

    @Test
    @DisplayName("회원가입 - 성공")
    public void 회원가입1() throws Exception {

        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("123123");
        requestDto.setUsername("홍길동");
        requestDto.setNickname("HongGilDong");

        String data = mapper.writeValueAsString(requestDto);

        //when
        final ResultActions result =
            mvc.perform(post("/api/v1/members/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(data))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.email").value(requestDto.getEmail()));
    }

//    @Test
//    @DisplayName("회원가입 - 실패(유효성 실패)")
//    public void 회원가입2() throws Exception {
//
//        // given
//        SignupRequestDto requestDto = new SignupRequestDto();
//        requestDto.setEmail("test@test.com");
//        requestDto.setPassword("123123");
//        requestDto.setNickname("HongGilDong");
//
//        String data = mapper.writeValueAsString(requestDto);
//
//        //when
//        mvc.perform(post("/api/v1/members/auth")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(data))
//            .andDo(print())
//            .andExpect(status().is5xxServerError())
//            .andExpect(result -> assertTrue(
//                result.getResolvedException() instanceof DataIntegrityViolationException));
//    }


}