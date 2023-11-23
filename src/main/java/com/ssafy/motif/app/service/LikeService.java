package com.ssafy.motif.app.service;

import com.ssafy.motif.app.domain.dto.like.Like;

public interface LikeService {
	void like(Long postId, String email);
	int likeCount(Long postId);
}
