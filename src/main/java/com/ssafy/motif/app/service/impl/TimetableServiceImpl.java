package com.ssafy.motif.app.service.impl;

import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.domain.dto.member.LoginResponseDto;
import com.ssafy.motif.app.domain.dto.schedule.ScheduleResponseDto;
import com.ssafy.motif.app.domain.dto.timetable.TimetableCreateRequestDto;
import com.ssafy.motif.app.domain.mapper.MemberMapper;
import com.ssafy.motif.app.domain.mapper.ScheduleMapper;
import com.ssafy.motif.app.domain.mapper.TimetableMapper;
import com.ssafy.motif.app.exception.TimetableDeduplicationException;
import com.ssafy.motif.app.exception.member.NotFoundMemberException;
import com.ssafy.motif.app.service.TimetableService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {

    private final MemberMapper memberMapper;
    private final ScheduleMapper scheduleMapper;
    private final TimetableMapper timetableMapper;

    @Override
    public Long save(TimetableCreateRequestDto requestDto, String email) {
        LoginResponseDto loginInfo = getLoginResponseDto(email);
        if (timetableMapper.getTimetableId(loginInfo.getMemberId(), requestDto.getDateTime()) != null) {
            throw new TimetableDeduplicationException(ErrorCode.TIMETABLE_DUPLICATE_ERROR);
        }

        timetableMapper.save(loginInfo.getMemberId(), requestDto);
        return requestDto.getTimetableId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getTableByDate(@Nullable LocalDateTime dateTime, String email) {
        LoginResponseDto loginInfo = getLoginResponseDto(email);
        Long timetableId = timetableMapper.getTimetableId(loginInfo.getMemberId(), dateTime);
        return scheduleMapper.fetchItemByTimetableId(timetableId);
    }

    private LoginResponseDto getLoginResponseDto(String email) {
        LoginResponseDto loginInfo = memberMapper.findByEmail(email).orElseThrow(
            () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND_BY_EMAIL)
        );
        return loginInfo;
    }
}
