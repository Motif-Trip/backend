package com.ssafy.motif.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.motif.app.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profileImages")
public class ImageController {
	
	private final ImageService imageService;
	
	// 프사 등록
	@PostMapping
	public ResponseEntity<?> profilePicAdd(MultipartFile file, Authentication authentication){
		log.info(file.getOriginalFilename());
		
		imageService.profilePicAdd(file, authentication.getName());
		
		return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.CREATED);
	}
	
	// 프사 불러오기(BufferedImage)
	@GetMapping
	public ResponseEntity<?> profilePicLoad(@PathVariable Authentication authentication){
		return new ResponseEntity<>(imageService.profilePicLoad(authentication.getName()), HttpStatus.OK);
	}
	
}
