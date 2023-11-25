package com.ssafy.motif.app.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

    void unlike(@Param("likeId") Long likeId);

    int likeCount(@Param("postId") Long postId);

    Long isLiked(@Param("postId") Long postId, @Param("email") String email);

    void like(@Param("postId") Long postId, @Param("email") String email);
}
