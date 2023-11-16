package com.ssafy.motif.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.motif.app.dto.post.Post;

@Mapper
public interface PostMapper {
	void postWrite(Post post);
	List<Post> postList();
	Post postSelect(Long postId);
	void postModify(Post post);
	void postDelete(Long postId);
}
