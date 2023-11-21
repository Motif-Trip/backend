package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.domain.dto.schedule.ScheduleCreateRequestDto;
import com.ssafy.motif.app.domain.mapper.ScheduleMapper;
import com.ssafy.motif.app.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper mapper;

    @Override
    public void create(ScheduleCreateRequestDto requestDto, String loggedInEmail) {
        mapper.save(requestDto, loggedInEmail);
    }
}
