package com.ssafy.motif.app.service;

import com.ssafy.motif.app.domain.dto.schedule.ScheduleResponseDto;
import com.ssafy.motif.app.domain.dto.timetable.TimetableCreateRequestDto;
import java.time.LocalDateTime;
import java.util.List;

public interface TimetableService {

    Long save(TimetableCreateRequestDto requestDto, String email);

    List<ScheduleResponseDto> getTableByDate(LocalDateTime dateTime, String email);
}
