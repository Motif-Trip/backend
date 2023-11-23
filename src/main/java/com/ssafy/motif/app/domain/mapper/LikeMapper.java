package com.ssafy.motif.app.domain.mapper;

import com.ssafy.motif.app.domain.dto.like.Like;

public interface LikeMapper {
	void like(Like like);
	Long isLiked(Like like);
	void unlike(Long likeId);
	int likeCount(Long postId);
}
