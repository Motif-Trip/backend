package com.ssafy.motif.app.service;

import com.ssafy.motif.app.domain.dto.post.PostRequestDto;
import java.util.List;

public interface PostService {
	void create(PostRequestDto postRequestDto, String email);
	List<PostRequestDto> postList();
	PostRequestDto postSelect(Long postId);
	void postModify(PostRequestDto postRequestDto);
	void postDelete(Long postId);
}

