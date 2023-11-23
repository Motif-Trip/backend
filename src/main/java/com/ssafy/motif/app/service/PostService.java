package com.ssafy.motif.app.service;

import com.ssafy.motif.app.domain.dto.post.PagingResponse;
import com.ssafy.motif.app.domain.dto.post.PostCreateRequestDto;
import java.util.List;

public interface PostService {

    List<PagingResponse> postPaging(int pageId, int size);

    void create(PostCreateRequestDto postCreateRequestDto, String email);

    PostCreateRequestDto postSelect(Long postId);

    void postModify(PostCreateRequestDto postCreateRequestDto);

    void postDelete(Long postId);
}

