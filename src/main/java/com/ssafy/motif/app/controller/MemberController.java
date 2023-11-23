package com.ssafy.motif.app.controller;

import com.ssafy.motif.app.code.ApiResponse;
import com.ssafy.motif.app.code.ResponseCode;
import com.ssafy.motif.app.domain.dto.jwt.TokenDto;
import com.ssafy.motif.app.domain.dto.member.LoginRequestDto;
import com.ssafy.motif.app.domain.dto.member.MemberResponseDto;
import com.ssafy.motif.app.domain.dto.member.SignupRequestDto;
import com.ssafy.motif.app.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final ApiResponse apiResponse;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.signup(requestDto);
        return apiResponse.success(ResponseCode.SIGNUP_SUCCESS.getMessage(), responseDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 성공 시, 쿠키에 Access-Token, Refresh-Token 저장")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        TokenDto tokenDto = memberService.login(requestDto, response);
        return apiResponse.success(ResponseCode.LOGIN_SUCCESS.getMessage(), tokenDto);
    }

    @GetMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 시, 토큰들 모두 삭제")
    public ResponseEntity<?> logout(@ApiIgnore HttpServletResponse response) {
        memberService.logout(response);
        return apiResponse.success(ResponseCode.LOGOUT_SUCCESS.getMessage());
    }
}
