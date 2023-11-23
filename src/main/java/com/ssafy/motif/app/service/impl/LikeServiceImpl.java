package com.ssafy.motif.app.service.impl;

import org.springframework.stereotype.Service;

import com.ssafy.motif.app.domain.dto.like.Like;
import com.ssafy.motif.app.domain.mapper.LikeMapper;
import com.ssafy.motif.app.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
	private final LikeMapper likeMapper;
	@Override
	public void like(Long postId, String email) {
		Like like=Like.builder()
				.postId(postId)
				.email(email)
				.build();

		Long likeId=likeMapper.isLiked(like);
		
		if(likeId!=null) { // 이미 좋아요를 했다.
			log.info("해당 좋아요를 취소함. like id:"+likeId);
			// 좋아요 취소
			likeMapper.unlike(likeId);
		}else {
			log.info("좋아요 처리");
			likeMapper.like(like);
		}
		
	}

	@Override
	public int likeCount(Long postId) {
		// TODO Auto-generated method stub
		return likeMapper.likeCount(postId);
	}
	
}
