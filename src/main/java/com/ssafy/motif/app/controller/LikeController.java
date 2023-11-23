package com.ssafy.motif.app.controller;

import com.ssafy.motif.app.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
	private final LikeService likeService;
	
	// 좋아요/취소
	@PostMapping("/like/{postId}")
	public ResponseEntity<?> like(@PathVariable Long postId, @ApiIgnore Authentication authentication){
		likeService.like(postId, authentication.getName());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 해당 게시물의 좋아요 카운트
	@GetMapping("/likecount/{postId}")
	public ResponseEntity<?> likeCount(@PathVariable Long postId, @ApiIgnore Authentication authentication){
		return ResponseEntity.ok(likeService.likeCount(postId));
	}
}