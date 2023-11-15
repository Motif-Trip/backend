package com.ssafy.motif.app.dto.post;

import lombok.Data;

@Data
public class Post {
	private Long postId;
	private String contents;
	private Long memberId;
}
