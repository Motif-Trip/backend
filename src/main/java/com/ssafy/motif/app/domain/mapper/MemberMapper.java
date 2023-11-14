package com.ssafy.motif.app.domain.mapper;

import com.ssafy.motif.app.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    Member findMember(@Param("id") Long id);
}
