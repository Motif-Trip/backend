package com.ssafy.motif.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.motif.app.dto.comment.Comment;
import com.ssafy.motif.app.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/api")
public class CommentController {
	private final CommentService commentService;
	
	// 댓글 작성하기
	@PostMapping("/write")
	public ResponseEntity<?> commentWrite(Comment comment){
		commentService.commentWrite(comment);
		
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}
	
	// 답글 작성하기
	@PostMapping("/reply")
	public ResponseEntity<?> replyWrite(Comment comment){
		commentService.replyWrite(comment);
		
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}
	
	// 특정 게시물의 댓글들 불러오기
	@GetMapping("/list/{postId}")
	public ResponseEntity<?> commentList(@PathVariable Long postId){
		return new ResponseEntity<>(
				commentService.commentList(postId), HttpStatus.OK);
	}
	
	// 댓글 수정하기
	@PutMapping("/modify/{commentId}")
	public ResponseEntity<?> commentModify(@PathVariable Long commentId){
		commentService.commentModify(commentId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// 댓글 삭제하기
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<?> commentDelete(@PathVariable Long commentId){
		commentService.commentDelete(commentId);
		
		// 해당 콘텐트는 더이상 무의미하다
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}