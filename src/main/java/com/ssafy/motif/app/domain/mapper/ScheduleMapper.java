package com.ssafy.motif.app.domain.mapper;

import com.ssafy.motif.app.domain.dto.schedule.ScheduleCreateRequestDto;
import com.ssafy.motif.app.domain.dto.schedule.ScheduleResponseDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScheduleMapper {

    /**
     * @param requestDto    : 스케쥴 정보
     * @param loggedInEmail : 요청자 이메일
     */
    void save(
        @Param("dto") ScheduleCreateRequestDto requestDto,
        @Param("email") String loggedInEmail);


    /**
     * @param timetableId   : 스케쥴이 담긴 테이블 ID
     * @return
     */
    List<ScheduleResponseDto> fetchItemByTimetableId(
        @Param("timetableId") Long timetableId
    );

}
