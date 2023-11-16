package com.ssafy.motif.app.service;

import com.ssafy.motif.app.dto.jwt.TokenDto;
import com.ssafy.motif.app.dto.member.LoginRequestDto;
import com.ssafy.motif.app.dto.member.MemberResponseDto;
import com.ssafy.motif.app.dto.member.SignupRequestDto;
import javax.servlet.http.HttpServletResponse;

public interface MemberService {

    MemberResponseDto signup(SignupRequestDto requestDto);

    TokenDto login(LoginRequestDto requestDto, HttpServletResponse response);
}
