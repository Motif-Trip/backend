package com.ssafy.motif.app.service;

import com.ssafy.motif.app.domain.dto.post.PostResponseDto;
import com.ssafy.motif.app.domain.dto.post.PostCreateRequestDto;
import java.util.List;

public interface PostService {

    List<PostResponseDto> getList(int page, String email);

    void create(PostCreateRequestDto postCreateRequestDto, String email);

    PostCreateRequestDto postSelect(Long postId);

    void postModify(PostCreateRequestDto postCreateRequestDto);

    void postDelete(Long postId);
}

