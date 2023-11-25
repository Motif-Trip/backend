package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.domain.mapper.LikeMapper;
import com.ssafy.motif.app.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;

    @Override
    public int like(Long postId, String email) {

        Long likeId = likeMapper.isLiked(postId, email);
        if (likeId != null) { // 이미 좋아요를 했다.
            log.info("해당 좋아요를 취소함. like id:" + likeId);
            likeMapper.unlike(likeId);
        } else {
            log.info("좋아요 처리");
            likeMapper.like(postId, email);
        }

        return likeMapper.likeCount(postId);
    }

}
