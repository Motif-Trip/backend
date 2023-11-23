package com.ssafy.motif.app.service;

import java.util.List;

import com.ssafy.motif.app.domain.dto.post.PostRequestDto;
import com.ssafy.motif.app.domain.dto.post.PagingResponse;

public interface PostService {
	void create(PostRequestDto postRequestDto, String email);
	List<PagingResponse> postPaging(int pageId, int size);
	PostRequestDto postSelect(Long postId);
	void postModify(PostRequestDto postRequestDto);
	void postDelete(Long postId);
}

