package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.domain.dto.schedule.ScheduleCreateRequestDto;
import com.ssafy.motif.app.domain.mapper.ScheduleMapper;
import com.ssafy.motif.app.domain.mapper.TimetableMapper;
import com.ssafy.motif.app.exception.NotFoundTimetableException;
import com.ssafy.motif.app.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final TimetableMapper timetableMapper;
    private final ScheduleMapper mapper;

    @Override
    public Long create(ScheduleCreateRequestDto requestDto, String loggedInEmail) {

        /* 시작 시간 및 종료 시간 유효성 검증*/
        requestDto.validateTime();

        /* 타임테이블 ID 조회 */
        Long timetableId = timetableMapper.getTimetableIdByEmail(loggedInEmail);
        if (timetableId == null) {
            throw new NotFoundTimetableException(ErrorCode.TIMETABLE_NOT_FOUND);
        }

        /* 타임테이블 저장 */
        mapper.save(timetableId, requestDto, loggedInEmail);
        return requestDto.getScheduleId();
    }
}
