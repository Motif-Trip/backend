package com.ssafy.motif.app.mapper;

import com.ssafy.motif.app.dto.member.MemberResponseDto;
import com.ssafy.motif.app.dto.member.SignupRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    void signup(@Param("dto") SignupRequestDto requestDto);

    boolean isEmailAlreadyInUse(@Param("email") String email);

    MemberResponseDto findById(Long memberId);
}
