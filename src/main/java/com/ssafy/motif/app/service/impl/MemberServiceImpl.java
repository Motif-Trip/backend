package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.dto.member.MemberResponseDto;
import com.ssafy.motif.app.dto.member.SignupRequestDto;
import com.ssafy.motif.app.exception.EmailDuplicateException;
import com.ssafy.motif.app.mapper.MemberMapper;
import com.ssafy.motif.app.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public MemberResponseDto signup(SignupRequestDto requestDto) {
        /* 이메일 중복 조사 */
        emailValidation(requestDto);
        /* 비밀번호 암호화 */
        requestDto.setPassword(encoder.encode(requestDto.getPassword()));
        /* 회원가입 수행  */
        mapper.signup(requestDto);
        /* MemberResponseDto 반환 */
        return mapper.findById(requestDto.getMemberId());
    }


    private void emailValidation(SignupRequestDto requestDto) {
        if (mapper.isEmailAlreadyInUse(requestDto.getEmail())) {
            throw new EmailDuplicateException("이미 사용 중인 이메일 주소입니다.");
        }
    }
}
