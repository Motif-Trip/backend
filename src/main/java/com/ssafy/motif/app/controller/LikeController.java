package com.ssafy.motif.app.controller;

import com.ssafy.motif.app.code.ApiResponse;
import com.ssafy.motif.app.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final ApiResponse apiResponse;
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<?> like(
        @RequestParam("postId") Long postId,
        @ApiIgnore Authentication authentication) {
        return apiResponse.success("좋아요 카운트", likeService.like(postId, authentication.getName()));
    }
}