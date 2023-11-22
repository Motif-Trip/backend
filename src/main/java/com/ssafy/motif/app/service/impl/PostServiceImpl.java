package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.domain.dto.member.LoginResponseDto;
import com.ssafy.motif.app.domain.dto.post.PostRequestDto;
import com.ssafy.motif.app.domain.mapper.MemberMapper;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ssafy.motif.app.domain.mapper.PostMapper;
import com.ssafy.motif.app.service.PostService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final MemberMapper memberMapper;

    @Override
    public void create(PostRequestDto requestDto, String email) {
        /* 이메일을 통해 회원 ID 조회 */
        Long memberId = memberMapper.findByEmail(email)
            .map(LoginResponseDto::getMemberId)
            .orElseThrow(() -> new IllegalArgumentException("이메일을 통한 회원 조회 실패 : " + email));

        /* 게시물 생성 */
        postMapper.save(requestDto, memberId);
        /* 생성이 됐다면, postId 에 키값이 담길 것.*/
        log.debug("게시물 생성 성공 : {}", requestDto);
    }

    @Override
    public List<PostRequestDto> postList() {
        return postMapper.postList();
    }

    @Override
    public PostRequestDto postSelect(Long postId) {
        // TODO Auto-generated method stub
        return postMapper.postSelect(postId);
    }

    @Override
    public void postModify(PostRequestDto postRequestDto) {
        // TODO Auto-generated method stub
        postMapper.postModify(postRequestDto);

    }

    @Override
    public void postDelete(Long postId) {
        // TODO Auto-generated method stub
        postMapper.postDelete(postId);
    }

}