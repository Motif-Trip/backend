package com.ssafy.motif.app.domain.dto.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Like {

	@JsonIgnore
	private Long likeId;

	private Long postId;

	private String email;
}