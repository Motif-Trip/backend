package com.ssafy.motif.app.domain.mapper;

import com.ssafy.motif.app.domain.dto.member.LoginResponseDto;
import com.ssafy.motif.app.domain.dto.member.MemberResponseDto;
import com.ssafy.motif.app.domain.dto.member.SignupRequestDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    void signup(@Param("dto") SignupRequestDto requestDto);

    boolean isEmailAlreadyInUse(@Param("email") String email);

    MemberResponseDto findById(@Param("memberId") Long memberId);

    Optional<LoginResponseDto> findByEmail(@Param("email") String email);
}
