package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.dto.jwt.TokenDto;
import com.ssafy.motif.app.dto.member.LoginRequestDto;
import com.ssafy.motif.app.dto.member.LoginResponseDto;
import com.ssafy.motif.app.dto.member.MemberResponseDto;
import com.ssafy.motif.app.dto.member.SignupRequestDto;
import com.ssafy.motif.app.exception.EmailDuplicateException;
import com.ssafy.motif.app.mapper.MemberMapper;
import com.ssafy.motif.app.mapper.RefreshTokenMapper;
import com.ssafy.motif.app.service.MemberService;
import com.ssafy.motif.app.util.cookie.CookieUtil;
import com.ssafy.motif.app.util.jwt.JwtProvider;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtProvider JwtProvider;
    private final CookieUtil cookieUtil;

    private final PasswordEncoder encoder;
    private final MemberMapper memberMapper;
    private final RefreshTokenMapper refreshTokenMapper;


    @Override
    @Transactional
    public MemberResponseDto signup(SignupRequestDto requestDto) {
        /* 이메일 중복 조사 */
        validateEmail(requestDto);
        /* 비밀번호 암호화 */
        requestDto.setPassword(encoder.encode(requestDto.getPassword()));
        /* 회원가입 수행  */
        memberMapper.signup(requestDto);
        /* MemberResponseDto 반환 */
        return memberMapper.findById(requestDto.getMemberId());
    }

    @Override
    @Transactional
    public TokenDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        LoginResponseDto member = memberMapper.findByEmail(requestDto.getEmail()).orElseThrow(
            () -> new IllegalArgumentException(requestDto.getEmail() + "= 존재하지 않는 회원입니다.")
        );

        if (!encoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = JwtProvider.generateTokenDto(member.getEmail());
        /* 쿠키에 액세스 토큰 추가 */
        cookieUtil.setCookie(
            "Access_Token",
            tokenDto.getAccessToken(),
            JwtProvider.getAccessTokenTime(), response
        );

        /* 쿠키에 리프레쉬 토큰 추가 */
        cookieUtil.setCookie(
            "Refresh_Token",
            tokenDto.getRefreshToken(),
            JwtProvider.getRefreshTokenTime(), response
        );

        /* 리프레쉬 토큰 갱신 */
        refreshTokenMapper.updateToken(
            member.getEmail(),
            tokenDto.getRefreshToken(),
            JwtProvider.extractExpirationAt(tokenDto.getRefreshToken())
        );

        /* 로그인 성공 */
        return tokenDto;
    }

    private void validateEmail(SignupRequestDto requestDto) {
        if (memberMapper.isEmailAlreadyInUse(requestDto.getEmail())) {
            throw new EmailDuplicateException("이미 사용 중인 이메일 주소입니다.");
        }
    }
}
