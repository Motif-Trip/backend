package com.ssafy.motif.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.motif.app.dto.post.Post;
import com.ssafy.motif.app.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public ResponseEntity<?> postWrite(Post post) {
        postService.postWrite(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> postList() {
        return ResponseEntity.ok(postService.postList());
    }


    @PostMapping("/select/{postId}")
    public ResponseEntity<?> postSelect(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.postSelect(postId));

    }

    @PostMapping("/modify")
    public ResponseEntity<?> postModify(Post post) {
        postService.postModify(post);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<?> postDelete(@PathVariable Long postId) {
        postService.postDelete(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
