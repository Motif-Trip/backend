package com.ssafy.motif.app.service;

import java.util.List;

import com.ssafy.motif.app.dto.post.Post;

public interface PostService {
	void postWrite(Post post);
	List<Post> postList();
	Post postSelect(Long postId);
	void postModify(Post post);
	void postDelete(Long postId);
}

