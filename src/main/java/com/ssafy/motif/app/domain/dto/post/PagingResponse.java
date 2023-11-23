package com.ssafy.motif.app.domain.dto.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class PagingResponse {
	private Long postId;
	private String contents;
	private Long memberId;
	private String createdAt;
}
