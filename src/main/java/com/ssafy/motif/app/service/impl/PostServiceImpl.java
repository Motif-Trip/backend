package com.ssafy.motif.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.motif.app.dto.post.Post;
import com.ssafy.motif.app.mapper.PostMapper;
import com.ssafy.motif.app.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	private final PostMapper postMapper;
	
	@Override
	public void postWrite(Post post) {
		postMapper.postWrite(post);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> postList() {
		return postMapper.postList();
	}

	@Override
	public Post postSelect(Long postId) {
		// TODO Auto-generated method stub
		return postMapper.postSelect(postId);
	}

	@Override
	public void postModify(Post post) {
		// TODO Auto-generated method stub
		postMapper.postModify(post);
		
	}

	@Override
	public void postDelete(Long postId) {
		// TODO Auto-generated method stub
		postMapper.postDelete(postId);
	}
	
}