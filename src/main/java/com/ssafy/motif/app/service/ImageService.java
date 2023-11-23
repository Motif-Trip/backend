package com.ssafy.motif.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.motif.app.domain.dto.image.ProfileImage;

public interface ImageService {
	
	String profilePicAdd(MultipartFile file, String email);
	
	ProfileImage profilePicLoad(String email);
	
	void profilePicUpdate(MultipartFile file, String email);
	
	void profilePicRemove(String email);
}
