package com.ssafy.motif.app.domain.dto.like;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Like {
	private Long likeId;
	private Long postId;
	private String email;
}