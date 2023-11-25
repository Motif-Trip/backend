package com.ssafy.motif.app.domain.mapper;

import com.ssafy.motif.app.domain.dto.post.PostCreateRequestDto;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.motif.app.domain.dto.post.PageRequest;
import com.ssafy.motif.app.domain.dto.post.PostResponseDto;

@Mapper
public interface PostMapper {

    /**
     * @param dto      : 제목, 내용
     * @param memberId : 작성자 - 참조키
     */
    void save(@Param("dto") PostCreateRequestDto dto, @Param("memberId") Long memberId);

    List<PostResponseDto> fetchPostList(@Param("request") PageRequest pageRequest, @Param("email") String email);

    PostCreateRequestDto postSelect(Long postId);

    void postModify(PostCreateRequestDto postRequestDto);

    void postDelete(Long postId);
}
