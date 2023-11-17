package com.ssafy.motif.app.dto.comment;

import lombok.Data;

@Data
public class Comment {
	private Long commentId;
	private Long postId;
	private Long memberId;
	private Long parentsId;
	private String contents;
}