package com.ssafy.motif.app.controller;

import com.ssafy.motif.app.code.ApiResponse;
import com.ssafy.motif.app.code.ResponseCode;
import com.ssafy.motif.app.domain.dto.post.PostCreateRequestDto;
import com.ssafy.motif.app.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final ApiResponse apiResponse;
    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시물 생성하기")
    public ResponseEntity<?> create(
        @RequestBody PostCreateRequestDto requestDto,
        @ApiIgnore Authentication authentication
    ) {
        log.info("포스트 생성 : {}", requestDto);
        postService.create(requestDto, authentication.getName());
        return apiResponse.success(ResponseCode.POST_CREATE_SUCCESS.getMessage(), requestDto);
    }


    @GetMapping("/paging/{pageId}")
    public ResponseEntity<?> postPaging(@PathVariable int pageId) {
        int size = 20; // 페이징 사이즈
        return ResponseEntity.ok(postService.postPaging(pageId, size));
    }

    @GetMapping("/select/{postId}")
    public ResponseEntity<?> postSelect(
        @PathVariable Long postId,
        @ApiIgnore Authentication authentication) {
        return ResponseEntity.ok(postService.postSelect(postId));

    }

    @PutMapping("/modify")
    public ResponseEntity<?> postModify(
        @RequestBody PostCreateRequestDto postCreateRequestDto,
        @ApiIgnore Authentication authentication) {
        postService.postModify(postCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> postDelete(
        @PathVariable Long postId,
        @ApiIgnore Authentication authentication) {
        postService.postDelete(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}