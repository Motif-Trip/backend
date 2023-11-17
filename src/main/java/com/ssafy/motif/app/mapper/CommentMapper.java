package com.ssafy.motif.app.mapper;

import java.util.List;

import com.ssafy.motif.app.dto.comment.Comment;

public interface CommentMapper {
	void commentWrite(Comment comment);
	void replyWrite(Comment comment);
	List<Comment> commentList(Long postId);
	void commentModify(Long postId);
	void commentDelete(Long postId);
}
