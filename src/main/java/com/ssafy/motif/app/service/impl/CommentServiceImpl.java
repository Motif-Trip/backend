package com.ssafy.motif.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.motif.app.dto.comment.Comment;
import com.ssafy.motif.app.mapper.CommentMapper;
import com.ssafy.motif.app.service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentMapper commentMapper;
	@Override
	public void commentWrite(Comment comment) {
		// TODO Auto-generated method stub
		commentMapper.commentWrite(comment);
	}

	@Override
	public void replyWrite(Comment comment) {
		commentMapper.replyWrite(comment);
	}
	
	@Override
	public List<Comment> commentList(Long postId) {
		// TODO Auto-generated method stub
		return commentMapper.commentList(postId);
	}

	@Override
	public void commentModify(Comment comment) {
		// TODO Auto-generated method stub
		commentMapper.commentModify(comment);

	}

	@Override
	public void commentDelete(Long commentId) {
		// TODO Auto-generated method stub
		commentMapper.commentDelete(commentId);
	}
}