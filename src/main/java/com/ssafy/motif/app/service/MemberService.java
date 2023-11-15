package com.ssafy.motif.app.service;

import com.ssafy.motif.app.dto.member.MemberResponseDto;
import com.ssafy.motif.app.dto.member.SignupRequestDto;

public interface MemberService {
    MemberResponseDto signup(SignupRequestDto requestDto);
}
