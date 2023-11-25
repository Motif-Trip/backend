package com.ssafy.motif.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.motif.app.domain.dto.member.LoginResponseDto;
import com.ssafy.motif.app.domain.dto.post.PostCreateRequestDto;
import com.ssafy.motif.app.domain.dto.post.PageRequest;
import com.ssafy.motif.app.domain.dto.post.PostResponseDto;
import com.ssafy.motif.app.domain.mapper.MemberMapper;
import com.ssafy.motif.app.domain.mapper.PostMapper;
import com.ssafy.motif.app.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final MemberMapper memberMapper;

    @Override
    public void create(PostCreateRequestDto requestDto, String email) {
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
    public List<PostResponseDto> getList(int page, String email) {
        return postMapper.fetchPostList(new PageRequest(page, 20), email);
    }

    @Override
    public PostCreateRequestDto postSelect(Long postId) {
        // TODO Auto-generated method stub
        return postMapper.postSelect(postId);
    }

    @Override
    public void postModify(PostCreateRequestDto postCreateRequestDto) {
        // TODO Auto-generated method stub
        postMapper.postModify(postCreateRequestDto);

    }

    @Override
    public void postDelete(Long postId) {
        // TODO Auto-generated method stub
        postMapper.postDelete(postId);
    }

}