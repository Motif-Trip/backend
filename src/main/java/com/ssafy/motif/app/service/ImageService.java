package com.ssafy.motif.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	String profilePicAdd(MultipartFile file, Long memberId);
}
