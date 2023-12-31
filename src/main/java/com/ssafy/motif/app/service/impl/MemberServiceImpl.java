package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.domain.dto.jwt.TokenDto;
import com.ssafy.motif.app.domain.dto.member.LoginRequestDto;
import com.ssafy.motif.app.domain.dto.member.LoginResponseDto;
import com.ssafy.motif.app.domain.dto.member.MemberResponseDto;
import com.ssafy.motif.app.domain.dto.member.SignupRequestDto;
import com.ssafy.motif.app.exception.member.EmailDuplicateException;
import com.ssafy.motif.app.domain.mapper.MemberMapper;
import com.ssafy.motif.app.domain.mapper.RefreshTokenMapper;
import com.ssafy.motif.app.exception.member.NotFoundMemberException;
import com.ssafy.motif.app.exception.member.PasswordMismatchException;
import com.ssafy.motif.app.service.MemberService;
import com.ssafy.motif.app.util.cookie.CookieUtil;
import com.ssafy.motif.app.util.jwt.JwtProvider;
import java.time.LocalDateTime;
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

    private final JwtProvider jwtProvider;
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
        log.debug("회원가입 성공");

        /* Empty Refresh-Token 저장 */
        refreshTokenMapper.createToken("", requestDto.getEmail(), LocalDateTime.now());
        log.debug("빈 리프레쉬 토큰 저장");

        /* MemberResponseDto 반환 */
        return memberMapper.findById(requestDto.getMemberId());
    }

    @Override
    @Transactional
    public TokenDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        LoginResponseDto member = memberMapper.findByEmail(requestDto.getEmail()).orElseThrow(
            () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND)
        );

        if (!encoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new PasswordMismatchException(ErrorCode.PASSWORD_MISS_MATCH);
        }

        TokenDto tokenDto = jwtProvider.generateTokenDto(member.getEmail());

        /* 쿠키에 액세스 토큰 추가 */
        cookieUtil.setCookie(
            "Access_Token",
            tokenDto.getAccessToken(),
            jwtProvider.getAccessTokenTime(), response
        );
        log.debug("========== 로그인 - New Access-Token");

        /* 쿠키에 리프레쉬 토큰 추가 */
        cookieUtil.setCookie(
            "Refresh_Token",
            tokenDto.getRefreshToken(),
            jwtProvider.getRefreshTokenTime(), response
        );
        log.debug("========== 로그인 - New Refresh-Token");

        /* 리프레쉬 토큰 갱신 */
        refreshTokenMapper.updateToken(
            tokenDto.getRefreshToken(),
            member.getEmail(),
            LocalDateTime.now().plusSeconds(jwtProvider.getRefreshTokenTime())
        );

        /* 로그인 성공 */
        return tokenDto;
    }

    @Override
    public void logout(HttpServletResponse response) {
        /* 토큰 모두 만료시간 0 */
        cookieUtil.removeCookie("Access_Token", response);
        cookieUtil.removeCookie("Refresh_Token", response);
    }

    private void validateEmail(SignupRequestDto requestDto) {
        if (memberMapper.isEmailAlreadyInUse(requestDto.getEmail())) {
            throw new EmailDuplicateException(ErrorCode.EMAIL_DUPLICATE);
        }
    }
}
