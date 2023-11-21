package com.ssafy.motif.app.controller;

import com.ssafy.motif.app.code.ApiResponse;
import com.ssafy.motif.app.domain.dto.schedule.ScheduleCreateRequestDto;
import com.ssafy.motif.app.domain.dto.schedule.ScheduleResponseDto;
import com.ssafy.motif.app.domain.dto.timetable.TimetableCreateRequestDto;
import com.ssafy.motif.app.service.ScheduleService;
import com.ssafy.motif.app.service.TimetableService;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/table")
public class TimetableController {

    private final ScheduleService scheduleService;
    private final TimetableService timeTableService;
    private final ApiResponse apiResponse;

    @GetMapping
    @Operation(summary = "스케쥴 불러오기", description = "날짜에 따라서 여행 스케쥴 불러오기 (default: 오늘 스케쥴)")
    public ResponseEntity<?> getSchedules(
        @RequestParam(required = false) @Nullable LocalDateTime dateTime,
        @ApiIgnore Authentication authentication) {
        List<ScheduleResponseDto> list = timeTableService.getTableByDate(dateTime,
            authentication.getName());
        return apiResponse.success(dateTime == null ? "오늘 여행 스케쥴" : printScheduleDay(dateTime),
            list);
    }

    @PostMapping
    @Operation(summary = "타임테이블 생성하기", description = "해당 날짜에 이미 테이블이 있을 경우 중복 생성 X")
    public ResponseEntity<?> create(
        @RequestBody TimetableCreateRequestDto requestDto,
        @ApiIgnore Authentication authentication) {
        log.debug(requestDto.toString());
        Long savedId = timeTableService.save(requestDto, authentication.getName());
        return apiResponse.success("타임테이블 생성 성공", savedId);
    }

    @PostMapping("/schedule")
    @Operation(summary = "스케쥴 추가", description = "타임테이블에 들어갈 여행 스케쥴 추가")
    public ResponseEntity<?> add(
        @RequestBody final ScheduleCreateRequestDto requestDto,
        @ApiIgnore Authentication authentication) {
        /* 스케쥴 새로 저장*/
        scheduleService.create(requestDto, authentication.getName());
        return apiResponse.success("스케쥴 추가 성공", requestDto);
    }

    private static String printScheduleDay(LocalDateTime dateTime) {
        return String.format("$d년 %s월 %d일 - 여행 스케쥴",
            dateTime.getYear(),
            dateTime.getMonth(),
            dateTime.getDayOfMonth());
    }
}
