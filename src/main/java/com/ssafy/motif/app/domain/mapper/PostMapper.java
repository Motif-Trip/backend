package com.ssafy.motif.app.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.motif.app.domain.dto.post.PageRequest;
import com.ssafy.motif.app.domain.dto.post.PostRequestDto;
import com.ssafy.motif.app.domain.dto.post.PagingResponse;

@Mapper
public interface PostMapper {

	/**
	 * @param dto : 제목, 내용
	 * @param memberId : 작성자 - 참조키
	 */
	void save(@Param("dto") PostRequestDto dto, @Param("memberId") Long memberId);

	List<PagingResponse> postPaging(PageRequest pageRequest);
	PostRequestDto postSelect(Long postId);
	void postModify(PostRequestDto postRequestDto);
	void postDelete(Long postId);
}
