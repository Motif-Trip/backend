package com.ssafy.motif.app.service;

import com.ssafy.motif.app.domain.dto.schedule.ScheduleCreateRequestDto;

public interface ScheduleService {

    Long create(ScheduleCreateRequestDto requestDto, String loggedInEmail);

}
