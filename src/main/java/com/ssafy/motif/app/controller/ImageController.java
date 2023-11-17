package com.ssafy.motif.app.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.motif.app.dto.image.ProfileImage;
import com.ssafy.motif.app.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images/api")
public class ImageController {
	private final ImageService imageService;
	
	// 프사 등록
	@PostMapping("/profilepic/{memberId}")
	public ResponseEntity<?> profilePicAdd(MultipartFile file, @PathVariable Long memberId){
		imageService.profilePicAdd(file, memberId);
		
		return new ResponseEntity<>(memberId, HttpStatus.CREATED);
	}
	
}
