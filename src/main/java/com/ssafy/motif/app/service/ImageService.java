package com.ssafy.motif.app.service;

import java.awt.Image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	String profilePicAdd(MultipartFile file, String email);
	Image profilePicLoad(String email);
}
