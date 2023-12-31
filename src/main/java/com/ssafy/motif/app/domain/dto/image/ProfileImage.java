package com.ssafy.motif.app.domain.dto.image;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileImage {
	private Long profileImageId;
	private Long memberId;
	private String email;
	private String originalName;
	private String storedName;
	private String imageUrl;
	// private byte[] imageData;
}
