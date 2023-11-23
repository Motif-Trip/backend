package com.ssafy.motif.app.service;

import com.ssafy.motif.app.domain.dto.jwt.TokenDto;
import com.ssafy.motif.app.domain.dto.member.LoginRequestDto;
import com.ssafy.motif.app.domain.dto.member.MemberResponseDto;
import com.ssafy.motif.app.domain.dto.member.SignupRequestDto;
import javax.servlet.http.HttpServletResponse;

public interface MemberService {

    MemberResponseDto signup(SignupRequestDto requestDto);

    TokenDto login(LoginRequestDto requestDto, HttpServletResponse response);

    void logout(HttpServletResponse response);
}
