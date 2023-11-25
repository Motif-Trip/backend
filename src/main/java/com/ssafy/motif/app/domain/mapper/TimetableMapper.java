package com.ssafy.motif.app.domain.mapper;

import com.ssafy.motif.app.domain.dto.timetable.TimetableCreateRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TimetableMapper {

    Long save(
        @Param("memberId") Long memberId,
        @Param("dto") TimetableCreateRequestDto requestDto
    );

    Long getTimetableId(
        @Param("memberId") Long memberId
    );

    Long getTimetableIdByEmail(
        @Param("email") String email
    );
}
